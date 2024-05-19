package com.makeappssimple.abhimanyu.detekt.rules

import com.makeappssimple.abhimanyu.detekt.rules.rules.UseTrailingComma
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class UseTrailingCommaTest(
    private val environment: KotlinCoreEnvironment,
) {
    @Test
    fun `do not report issue when function calls have trailing commas`() {
        val code = """
        fun test(s: Int, t: Int): String = "test"

        fun main() {
            test(
                s = 1, 
                t = 3,
            )
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 0
    }

    @Test
    fun `reports issue when function calls do not have trailing commas`() {
        val code = """
        fun test(s: Int, t: Int): String = "test"

        fun main() {
            test(
                s = 1, 
                t = 3
            )
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `do not report issue when lists have trailing commas`() {
        val code = """
        val list = listOf(
            "abc",
            "qer",
            "xyz",
        )
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 0
    }

    @Test
    fun `reports issue when lists do not have trailing commas`() {
        val code = """
        val list = listOf(
            "abc",
            "qer",
            "xyz"
        )
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    private fun getFindings(
        code: String,
    ): List<Finding> {
        return UseTrailingComma().compileAndLintWithContext(
            environment = environment,
            content = code,
        )
    }
}
