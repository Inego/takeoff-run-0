package org.inego.takeoffrun.common.language.inflection

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue

interface Inflector {
    fun inflect(features: Collection<GrammaticalFeatureValue>): String
}