package com.makeappssimple.abhimanyu.detekt.rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtValueArgument

private object FunctionParametersInSeparateLineConstants {
    const val ISSUE_DESCRIPTION = "Function parameters should be on separate lines."
    const val ISSUE_MESSAGE = "Each function parameter should be on a separate line."
}

public class FunctionParametersInSeparateLine(
    config: Config = Config.empty,
) : Rule(
    ruleSetConfig = config,
) {
    override val issue: Issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Style,
        description = FunctionParametersInSeparateLineConstants.ISSUE_DESCRIPTION,
        debt = Debt.FIVE_MINS,
    )

    override fun visitCallExpression(
        expression: KtCallExpression,
    ) {
        super.visitCallExpression(expression)

        expression.valueArguments.forEach { argument ->
            val argumentName = argument.getArgumentName()
            if (argumentName != null && argumentName.textContains('\n')) {
                reportIssue(argument)
            }
        }
    }

    private fun reportIssue(
        argument: KtValueArgument,
    ) {
        report(
            finding = CodeSmell(
                issue = issue,
                entity = Entity.from(
                    element = argument,
                ),
                message = FunctionParametersInSeparateLineConstants.ISSUE_MESSAGE,
            )
        )
    }
}
