package org.inego.takeoffrun.common.language.inflection

inline class StemFormProvider(private val stem: String) : FormProvider {
    override fun provide(source: String) = source.replace("@", stem)
}