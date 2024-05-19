package com.makeappssimple.abhimanyu.detekt.rules.provider

import com.makeappssimple.abhimanyu.detekt.rules.rules.InnerClass
import com.makeappssimple.abhimanyu.detekt.rules.rules.AvoidFunctionExpression
import com.makeappssimple.abhimanyu.detekt.rules.rules.TooManyFunctions
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class MyRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "project-convention"

    override fun instance(
        config: Config,
    ): RuleSet {
        return RuleSet(
            id = ruleSetId,
            rules = listOf(
                InnerClass(
                    config = config,
                ),
                TooManyFunctions(
                    config = config,
                ),
                AvoidFunctionExpression(
                    config = config,
                ),
            ),
        )
    }
}
