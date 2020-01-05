package org.inego.takeoffrun.common.language

interface GrammaticalFeature {
    val name: String
    val possibleValues: List<GrammaticalFeatureValue>
}