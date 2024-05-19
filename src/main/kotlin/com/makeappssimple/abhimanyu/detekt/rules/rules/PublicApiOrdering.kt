package com.makeappssimple.abhimanyu.detekt.rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.fir.analysis.checkers.toVisibilityOrNull
import org.jetbrains.kotlin.psi.KtClassBody
import org.jetbrains.kotlin.psi.KtClassInitializer
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.psiUtil.isPublic
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierType
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierTypeOrDefault

private object PublicApiOrderingConstants {
    const val ISSUE_DESCRIPTION = """
        Public properties should be defined before private properties and 
        public methods should be defined before private methods
    """
}

class PublicApiOrdering(
    config: Config = Config.empty,
) : Rule(
    ruleSetConfig = config,
) {
    private var firstNonPublicMethod: String? = null

    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Style,
        description = PublicApiOrderingConstants.ISSUE_DESCRIPTION,
        debt = Debt.FIVE_MINS,
    )

    override fun visitNamedFunction(
        function: KtNamedFunction,
    ) {
        super.visitNamedFunction(function)

        if (function.isPublic) {
            if (firstNonPublicMethod != null) {
                reportIssue(
                    function = function,
                    message = "${function.name} should be declared before ${firstNonPublicMethod.orEmpty()}.",
                )
            }
        } else if (firstNonPublicMethod == null) {
            firstNonPublicMethod = function.name
        }
    }

    private fun reportIssue(
        function: KtNamedFunction,
        message: String,
    ) {
        report(
            finding = CodeSmell(
                issue = issue,
                entity = Entity.from(
                    element = function,
                ),
                message = message,
            ),
        )
    }
}
