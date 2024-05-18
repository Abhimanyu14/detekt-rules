package com.makeappssimple.abhimanyu.detekt.rules.provider

import com.makeappssimple.abhimanyu.detekt.rules.rules.MyRule
import com.makeappssimple.abhimanyu.detekt.rules.rules.TooManyFunctionsRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class MasRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "MasRuleSet"

    override fun instance(
        config: Config,
    ): RuleSet {
        return RuleSet(
            id = ruleSetId,
            rules = listOf(
                MyRule(
                    config = config,
                ),
                TooManyFunctionsRule(
                    config = config,
                ),
            ),
        )
    }
}
