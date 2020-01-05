package org.inego.takeoffrun.common.language.impl

import org.inego.takeoffrun.common.language.Language

data class LanguageImpl(
        override val name: String,
        override val code: String
) : Language {
    override fun toString(): String = "$code ($name)"
}