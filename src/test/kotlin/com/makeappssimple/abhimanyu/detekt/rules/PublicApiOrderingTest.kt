package com.makeappssimple.abhimanyu.detekt.rules

import com.makeappssimple.abhimanyu.detekt.rules.rules.PublicApiOrdering
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class PublicApiOrderingTest(
    private val environment: KotlinCoreEnvironment,
) {
    @Test
    fun `reports if private function is declared before all the public functions`() {
        val code = """
        class Test {
            fun func1() {}
            private fun func2() {}
            fun func3() {}
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `reports if internal function is declared before all the public functions`() {
        val code = """
        class Test {
            fun func1() {}
            internal fun func2() {}
            fun func3() {}
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `reports if protected function is declared before all the public functions`() {
        val code = """
        class Test {
            fun func1() {}
            protected fun func2() {}
            fun func3() {}
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `does not report when all public functions are declared before non public functions`() {
        val code = """
        class Test {
            fun func1() {}
            fun func2() {}
            private fun func3() {}
            internal fun func4() {}
            protected fun func5() {}
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 0
    }

    @Disabled("To fix later")
    @Test
    fun `does not report when all public functions are declared before non public functions in nested anonymous object`() {
        val code = """
        public class AmountCommaVisualTransformation : VisualTransformation {
            override fun filter(): TransformedText {
                return TransformedText(
                    offsetMapping = object : OffsetMapping {
                        override fun originalToTransformed(
                            offset: Int,
                        ): Int { }
        
                        override fun transformedToOriginal(
                            offset: Int,
                        ): Int { }
                    }
                )
            }
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 0
    }

    private fun getFindings(
        code: String,
    ): List<Finding> {
        return PublicApiOrdering().compileAndLintWithContext(
            environment = environment,
            content = code,
        )
    }
}
