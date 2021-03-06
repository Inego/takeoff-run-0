package org.inego.takeoffrun.common.language

data class GrammaticalFeatureValue(
        val name: String,
        val shortName: String
) {
    override fun toString() = "$shortName ($name)"
}
