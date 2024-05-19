package com.makeappssimple.abhimanyu.detekt.rules

import com.makeappssimple.abhimanyu.detekt.rules.rules.AvoidFunctionExpression
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class AvoidFunctionExpressionTest(
    private val environment: KotlinCoreEnvironment,
) {
    @Test
    fun `reports function expression with return type`() {
        val code = """
        fun test(): String = "test"
        """

        val findings = AvoidFunctionExpression(
            config = Config.empty,
        ).compileAndLintWithContext(
            environment = environment,
            content = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `reports function expression without return type`() {
        val code = """
        fun test() = "test"
        """

        val findings = AvoidFunctionExpression(
            config = Config.empty,
        ).compileAndLintWithContext(
            environment = environment,
            content = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `does not report non function expression`() {
        val code = """
        fun test(): String {
            return "test"
        }
        """

        val findings = AvoidFunctionExpression(
            config = Config.empty,
        ).compileAndLintWithContext(
            environment = environment,
            content = code,
        )

        findings shouldHaveSize 0
    }
}
