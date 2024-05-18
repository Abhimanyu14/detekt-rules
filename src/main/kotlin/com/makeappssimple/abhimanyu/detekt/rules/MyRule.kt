package com.makeappssimple.abhimanyu.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtClass

class MyRule(config: Config) : Rule(config) {
    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.CodeSmell,
        description = "Custom Rule",
        debt = Debt.FIVE_MINS,
    )

    override fun visitClass(
        klass: KtClass,
    ) {
        super.visitClass(klass)

        if (klass.isInner()) {
            report(
                finding = CodeSmell(
                    issue = issue,
                    entity = Entity.atName(klass),
                    message = "Custom message",
                )
            )
        }
    }
}
