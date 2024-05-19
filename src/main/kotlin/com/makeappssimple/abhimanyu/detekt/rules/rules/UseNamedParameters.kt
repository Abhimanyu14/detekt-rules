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

private object UseNamedParametersConstants {
    const val ISSUE_DESCRIPTION = "Use of named parameters wherever applicable."
    const val ISSUE_MESSAGE = "Function call arguments should use named parameters."
}

public class UseNamedParameters(
    config: Config = Config.empty,
) : Rule(
    ruleSetConfig = config,
) {
    override val issue: Issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Style,
        description = UseNamedParametersConstants.ISSUE_DESCRIPTION,
        debt = Debt.FIVE_MINS,
    )

    override fun visitCallExpression(
        expression: KtCallExpression,
    ) {
        super.visitCallExpression(expression)

        expression.valueArguments.forEach { argument ->
            if (argument.getArgumentName() == null) {
                reportIssue(
                    argument = argument,
                )
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
                message = UseNamedParametersConstants.ISSUE_MESSAGE,
            )
        )
    }
}
