package com.makeappssimple.abhimanyu.detekt.rules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.config
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

private object TooManyFunctionsConstants {
    const val ISSUE_DESCRIPTION = "This rule reports a file with an excessive function count."
    const val ISSUE_MESSAGE = "Too many functions can make the maintainability of a file costlier"
    const val DEFAULT_MAX_ALLOWED_NUMBER_OF_METHODS = 10
}

class TooManyFunctions(
    config: Config,
) : Rule(
    ruleSetConfig = config,
) {
    private val maxAllowedNumberOfMethods: Int by config(
        defaultValue = TooManyFunctionsConstants.DEFAULT_MAX_ALLOWED_NUMBER_OF_METHODS,
    )
    private var numberOfMethods: Int = 0

    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.CodeSmell,
        description = TooManyFunctionsConstants.ISSUE_DESCRIPTION,
        debt = Debt.TWENTY_MINS,
    )

    override fun visitKtFile(
        file: KtFile,
    ) {
        super.visitKtFile(file)
        if (numberOfMethods > maxAllowedNumberOfMethods) {
            reportIssue(
                file = file,
            )
        }
        numberOfMethods = 0
    }

    override fun visitNamedFunction(
        function: KtNamedFunction,
    ) {
        super.visitNamedFunction(function)
        numberOfMethods++
    }

    private fun reportIssue(
        file: KtFile,
    ) {
        report(
            finding = CodeSmell(
                entity = Entity.from(
                    element = file,
                ),
                issue = issue,
                message = TooManyFunctionsConstants.ISSUE_MESSAGE,
            ),
        )
    }
}
