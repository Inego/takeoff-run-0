package org.inego.takeoffrun.common.language

import org.inego.takeoffrun.common.language.inflection.Inflector

interface Word : Inflector {
    val entryName: String
    val partOfSpeech: PartOfSpeech

    val inherentFeatures: List<GrammaticalFeatureValue>
}