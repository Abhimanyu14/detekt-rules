package com.makeappssimple.abhimanyu.detekt.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class MyRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "MyRuleSet"

    override fun instance(
        config: Config,
    ): RuleSet {
        return RuleSet(
            id = ruleSetId,
            rules = listOf(
                MyRule(
                    config = config,
                ),
            ),
        )
    }
}
