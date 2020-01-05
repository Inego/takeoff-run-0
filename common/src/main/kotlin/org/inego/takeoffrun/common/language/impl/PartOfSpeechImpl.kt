package org.inego.takeoffrun.common.language.impl

import org.inego.takeoffrun.common.language.PartOfSpeech

data class PartOfSpeechImpl(override val name: String, override val shortName: String) : PartOfSpeech {
    override fun toString() = "$shortName ($name)"
}