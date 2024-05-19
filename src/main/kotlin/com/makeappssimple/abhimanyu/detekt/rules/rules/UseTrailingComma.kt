package com.makeappssimple.abhimanyu.detekt.rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.PsiComment
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtParameterList
import org.jetbrains.kotlin.psi.KtValueArgumentList

private object UseTrailingCommaConstants {
    const val ISSUE_DESCRIPTION = "Use of trailing commas in multiline lists and function calls."
    const val ISSUE_MESSAGE = "Trailing comma missing in a multiline list or function call."
}

class UseTrailingComma(
    config: Config = Config.empty,
) : Rule(
    ruleSetConfig = config,
) {
    override val issue: Issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Style,
        description = UseTrailingCommaConstants.ISSUE_DESCRIPTION,
        debt = Debt.FIVE_MINS,
    )

    override fun visitValueArgumentList(
        list: KtValueArgumentList,
    ) {
        super.visitValueArgumentList(list)
        enforceTrailingComma(
            element = list,
        )
    }

    override fun visitParameterList(
        list: KtParameterList,
    ) {
        super.visitParameterList(list)
        enforceTrailingComma(
            element = list,
        )
    }

    override fun visitCollectionLiteralExpression(
        expression: KtCollectionLiteralExpression,
    ) {
        super.visitCollectionLiteralExpression(expression)
        enforceTrailingComma(
            element = expression,
        )
    }

    private fun enforceTrailingComma(
        element: KtElement,
    ) {
        if (element.children.isNotEmpty() && element.textContains('\n')) {
            val lastArgument = when (element) {
                is KtValueArgumentList,
                is KtParameterList,
                -> element.children.lastOrNull {
                    it !is PsiWhiteSpace && it !is PsiComment
                }

                is KtCollectionLiteralExpression -> element.children.lastOrNull()
                else -> null
            }

            if (lastArgument != null && lastArgument.node.elementType != KtTokens.COMMA) {
                reportIssue(
                    element = element,
                )
            }
        }
    }


    private fun reportIssue(
        element: KtElement,
    ) {
        report(
            finding = CodeSmell(
                issue = issue,
                entity = Entity.from(
                    element = element,
                ),
                message = UseTrailingCommaConstants.ISSUE_MESSAGE,
            ),
        )
    }
}
