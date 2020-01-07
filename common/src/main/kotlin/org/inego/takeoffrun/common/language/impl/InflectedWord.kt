package org.inego.takeoffrun.common.language.impl

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue
import org.inego.takeoffrun.common.language.PartOfSpeech
import org.inego.takeoffrun.common.language.inflection.Inflector

class InflectedWord(
        entryName: String,
        partOfSpeech: PartOfSpeech,
        inherentFeatures: List<GrammaticalFeatureValue>,
        private val inflector: Inflector
) : BaseWord(entryName, partOfSpeech, inherentFeatures) {

    override fun inflect(features: Collection<GrammaticalFeatureValue>) = inflector.inflect(features)
}