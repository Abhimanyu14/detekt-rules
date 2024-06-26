package com.makeappssimple.abhimanyu.detekt.rules

import com.makeappssimple.abhimanyu.detekt.rules.rules.InnerClass
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class InnerClassTest(
    private val environment: KotlinCoreEnvironment,
) {
    @Test
    fun `reports inner classes`() {
        val code = """
        class A {
          inner class B
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `doesn't report inner classes`() {
        val code = """
        class A {
          class B
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
        return InnerClass().compileAndLintWithContext(
            environment = environment,
            content = code,
        )
    }
}
