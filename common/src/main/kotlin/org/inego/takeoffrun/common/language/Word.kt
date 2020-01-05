package org.inego.takeoffrun.common.language

interface Word {
    val partOfSpeech: PartOfSpeech

    val inherentFeatures: List<GrammaticalFeatureValue>
}