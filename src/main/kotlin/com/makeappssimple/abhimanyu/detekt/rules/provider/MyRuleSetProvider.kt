package com.makeappssimple.abhimanyu.detekt.rules.provider

import com.makeappssimple.abhimanyu.detekt.rules.rules.InnerClass
import com.makeappssimple.abhimanyu.detekt.rules.rules.AvoidFunctionExpression
import com.makeappssimple.abhimanyu.detekt.rules.rules.FunctionParametersInSeparateLine
import com.makeappssimple.abhimanyu.detekt.rules.rules.PublicApiOrdering
import com.makeappssimple.abhimanyu.detekt.rules.rules.UseNamedParameters
import com.makeappssimple.abhimanyu.detekt.rules.rules.TooManyFunctions
import com.makeappssimple.abhimanyu.detekt.rules.rules.UseTrailingComma
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

public class MyRuleSetProvider : RuleSetProvider {
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
                UseNamedParameters(
                    config = config,
                ),
                UseTrailingComma(
                    config = config,
                ),
                FunctionParametersInSeparateLine(
                    config = config,
                ),
                PublicApiOrdering(
                    config = config,
                ),
            ),
        )
    }
}
