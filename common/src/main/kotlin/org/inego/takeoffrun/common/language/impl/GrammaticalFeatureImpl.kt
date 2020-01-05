package org.inego.takeoffrun.common.language.impl

import org.inego.takeoffrun.common.language.GrammaticalFeature
import org.inego.takeoffrun.common.language.GrammaticalFeatureValue

class GrammaticalFeatureImpl(
        override val name: String,
        override val possibleValues: List<GrammaticalFeatureValue>
) : GrammaticalFeature {
    override fun toString() = "$name (${possibleValues.joinToString(",")})"
}