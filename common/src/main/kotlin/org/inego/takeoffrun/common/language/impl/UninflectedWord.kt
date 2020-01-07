package org.inego.takeoffrun.common.language.impl

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue
import org.inego.takeoffrun.common.language.PartOfSpeech

class UninflectedWord(
        partOfSpeech: PartOfSpeech,
        inherentFeatures: List<GrammaticalFeatureValue>,
        private val word: String
) : BaseWord(partOfSpeech, inherentFeatures) {
    override fun inflect(features: Collection<GrammaticalFeatureValue>) = word
}