package com.makeappssimple.abhimanyu.detekt.rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtClass

private object InnerClassConstants {
    const val ISSUE_DESCRIPTION = "Inner classes are not allowed"
    const val ISSUE_MESSAGE = "This is a test custom detekt rule"
}

class InnerClass(
    config: Config,
) : Rule(
    ruleSetConfig = config,
) {
    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.CodeSmell,
        description = InnerClassConstants.ISSUE_DESCRIPTION,
        debt = Debt.FIVE_MINS,
    )

    override fun visitClass(
        klass: KtClass,
    ) {
        super.visitClass(klass)

        if (klass.isInner()) {
            report(
                finding = CodeSmell(
                    entity = Entity.atName(
                        element = klass,
                    ),
                    issue = issue,
                    message = InnerClassConstants.ISSUE_MESSAGE,
                )
            )
        }
    }
}
