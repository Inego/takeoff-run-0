package org.inego.takeoffrun.common.language.impl

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue
import org.inego.takeoffrun.common.language.PartOfSpeech
import org.inego.takeoffrun.common.language.Word

abstract class BaseWord(
        override val entryName: String,
        override val partOfSpeech: PartOfSpeech,
        override val inherentFeatures: List<GrammaticalFeatureValue>
) : Word