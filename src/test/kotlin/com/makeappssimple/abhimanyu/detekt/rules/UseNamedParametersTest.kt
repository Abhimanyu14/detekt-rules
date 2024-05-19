package com.makeappssimple.abhimanyu.detekt.rules

import com.makeappssimple.abhimanyu.detekt.rules.rules.UseNamedParameters
import com.makeappssimple.abhimanyu.detekt.rules.rules.UseTrailingComma
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class UseNamedParametersTest(
    private val environment: KotlinCoreEnvironment,
) {
    @Test
    fun `do not report issue when function called with named parameters`() {
        val code = """
        fun test(s: Int, t: Int): String = "test"

        fun main() {
            test(s = 1, t = 3)
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 0
    }

    @Test
    fun `reports issue when function called without named parameters`() {
        val code = """
        fun test(s: Int, t: Int): String = "test"

        fun main() {
            test(1, 3)
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 2
    }

    @Test
    fun `reports issue when function called with only some named parameters`() {
        val code = """
        fun test(s: Int, t: Int): String = "test"

        fun main() {
            test(s = 1, 3)
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `reports issue when function called with only some named parameters in any order`() {
        val code = """
        fun test(s: Int, t: Int): String = "test"

        fun main() {
            test(1, t = 3)
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    private fun getFindings(
        code: String,
    ): List<Finding> {
        return UseNamedParameters().compileAndLintWithContext(
            environment = environment,
            content = code,
        )
    }
}
