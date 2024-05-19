package com.makeappssimple.abhimanyu.detekt.rules

import com.makeappssimple.abhimanyu.detekt.rules.rules.AvoidFunctionExpression
import com.makeappssimple.abhimanyu.detekt.rules.rules.InnerClass
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Finding
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

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `reports function expression inside object`() {
        val code = """
        public object CoroutineDispatcherModule {
            public fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
        
            public fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
        
            public fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
        
            public fun providesMainImmediateDispatcher(): CoroutineDispatcher {
                return Dispatchers.Main.immediate
            }
        
            public fun providesUnconfinedDispatcher(): CoroutineDispatcher {
                return Dispatchers.Unconfined
            }
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 3
    }

    @Test
    fun `reports function expression inside class`() {
        val code = """
        public class CoroutineDispatcherModule {
            public fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
        
            public fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
        
            public fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
        
            public fun providesMainImmediateDispatcher(): CoroutineDispatcher {
                return Dispatchers.Main.immediate
            }
        
            public fun providesUnconfinedDispatcher(): CoroutineDispatcher {
                return Dispatchers.Unconfined
            }
        }
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 3
    }

    @Test
    fun `reports function expression in extension method`() {
        val code = """
        public fun TransactionForEntity.asExternalModel(): TransactionFor = TransactionFor(
            id = id,
            title = title,
        )
        """

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 1
    }

    @Test
    fun `reports function expression without return type`() {
        val code = """
        fun test() = "test"
        """

        val findings = getFindings(
            code = code,
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

        val findings = getFindings(
            code = code,
        )

        findings shouldHaveSize 0
    }

    private fun getFindings(
        code: String,
    ): List<Finding> {
        return AvoidFunctionExpression().compileAndLintWithContext(
            environment = environment,
            content = code,
        )
    }
}
