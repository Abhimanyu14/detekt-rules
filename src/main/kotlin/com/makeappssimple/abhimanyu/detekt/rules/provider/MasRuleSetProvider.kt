package com.makeappssimple.abhimanyu.detekt.rules.provider

import com.makeappssimple.abhimanyu.detekt.rules.rules.InnerClass
import com.makeappssimple.abhimanyu.detekt.rules.rules.NoFunctionExpression
import com.makeappssimple.abhimanyu.detekt.rules.rules.TooManyFunctions
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
                InnerClass(
                    config = config,
                ),
                TooManyFunctions(
                    config = config,
                ),
                NoFunctionExpression(
                    config = config,
                ),
            ),
        )
    }
}
