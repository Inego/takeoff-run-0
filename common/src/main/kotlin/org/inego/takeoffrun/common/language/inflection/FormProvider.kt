package org.inego.takeoffrun.common.language.inflection

interface FormProvider {
    fun provide(source: String): String
}