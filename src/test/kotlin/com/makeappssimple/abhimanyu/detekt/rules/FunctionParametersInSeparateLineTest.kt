package com.makeappssimple.abhimanyu.detekt.rules

import com.makeappssimple.abhimanyu.detekt.rules.rules.FunctionParametersInSeparateLine
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class FunctionParametersInSeparateLineTest(
    private val environment: KotlinCoreEnvironment,
) {
    @Test
    fun `reports function expression with return type`() {
        val code = """
        fun test(s: Int, t: Int, u: Int, v: String, w: Boolean): String = "test"
        
        test("s", "t", "u", "v", true)
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    private fun getFindings(
        code: String,
    ): List<Finding> {
        return FunctionParametersInSeparateLine().compileAndLintWithContext(
            environment = environment,
            content = code,
        )
    }
}
