package com.makeappssimple.abhimanyu.detekt.rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.config
import io.gitlab.arturbosch.detekt.api.internal.Configuration
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtBinaryExpression
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtDeclarationWithBody
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtReturnExpression
import org.jetbrains.kotlin.psi.psiUtil.anyDescendantOfType

private object ExpressionBodySyntaxConstants {
    const val ISSUE_DESCRIPTION = "Function expressions should be avoided."
    const val ISSUE_MESSAGE = "Function expressions should not be used."
}

/**
 * Functions which only contain a `return` statement can be collapsed to an expression body. This shortens and
 * cleans up the code.
 *
 * <noncompliant>
 * fun stuff() = 5
 * </noncompliant>
 *
 * <compliant>
 * fun stuff(): Int {
 *   return 5
 * }
 *
 * fun stuff() {
 *     return
 *         moreStuff()
 *             .getStuff()
 *             .stuffStuff()
 * }
 * </compliant>
 */
class NoFunctionExpression(
    config: Config,
) : Rule(
    ruleSetConfig = config,
) {
    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.CodeSmell,
        description = ExpressionBodySyntaxConstants.ISSUE_DESCRIPTION,
        debt = Debt.FIVE_MINS,
    )

    override fun visitNamedFunction(
        function: KtNamedFunction,
    ) {
        if (function.isLocal) {
            report(
                finding = CodeSmell(
                    issue = issue,
                    entity = Entity.from(function),
                    message = ExpressionBodySyntaxConstants.ISSUE_MESSAGE,
                ),
            )
        }
    }
}
