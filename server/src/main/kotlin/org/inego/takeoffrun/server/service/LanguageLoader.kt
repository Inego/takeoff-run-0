package org.inego.takeoffrun.server.service

import io.ktor.util.extension
import org.apache.logging.log4j.kotlin.logger
import org.inego.takeoffrun.common.language.GrammaticalFeature
import org.inego.takeoffrun.common.language.GrammaticalFeatureValue
import org.inego.takeoffrun.common.language.LanguageBase
import org.inego.takeoffrun.common.language.PartOfSpeech
import org.inego.takeoffrun.common.language.impl.GrammaticalFeatureImpl
import org.inego.takeoffrun.common.language.impl.LanguageImpl
import org.inego.takeoffrun.common.language.impl.PartOfSpeechImpl
import org.inego.takeoffrun.server.utils.StringAny
import java.nio.file.Files
import java.nio.file.Path

object LanguageLoader {

    private val log = logger("LanguageLoader")

    fun load(path: Path): List<LanguageBase> {
        log.info("Loading languages from $path...")

        log.info("Reading the list of languages...")

        val languagesSrc = objectMapper.readValue(path.resolve("languages.yaml").toFile(), List::class.java)

        val languages = languagesSrc
                .filterIsInstance<StringAny>()
                .map { LanguageImpl(it["name"] as String, it["code"] as String) }

        log.info("Number of languages in the list: ${languages.size}")

        for (language in languages) {
            log.info("Loading base for $language...")
            loadLanguage(path.resolve(language.code))
            log.info("Loading base for $language finished.")
        }

        log.info("Languages loaded.")
        // TODO
        return emptyList()
    }

    private fun loadLanguage(path: Path) {
        log.info("Loading from $path...")

        val loadState = LanguageLoadState()

        Files.walk(path, 1)
                .filter { it.extension in YAML_EXTENSIONS }
                .forEach {
                    loadLanguageFromFile(it, loadState)
                }

        loadState.importGrammaticalFeatures()
        loadState.importPartsOfSpeech()

        log.info("Loading from $path finished.")
    }

    private fun loadLanguageFromFile(filePath: Path, loadState: LanguageLoadState) {
        log.info("Reading $filePath")
        @Suppress("UNCHECKED_CAST")
        val fileMap = objectMapper.readValue(filePath.toFile(), Map::class.java) as Map<String, Any>
        for ((key, value) in fileMap) {
            loadState.appendElement(key, value)
        }
    }
}

class LanguageLoadState {
    private val elements = hashMapOf<String, ArrayList<Any>>()

    private val grammaticalFeatures = arrayListOf<GrammaticalFeature>()
    private val partsOfSpeech = arrayListOf<PartOfSpeech>()

    fun appendElement(elementName: String, value: Any) {
        elements.computeIfAbsent(elementName) { arrayListOf() }
                .add(value)
    }

    fun importGrammaticalFeatures() {

        val blocks = elements.getOrElse("grammaticalFeatures") { emptyList<Any>() }
        for (block in blocks) {
            @Suppress("UNCHECKED_CAST")
            for (featureSrc in block as List<StringAny>) {
                val name = featureSrc["name"] as String
                val values = featureSrc["values"] as List<StringAny>

                grammaticalFeatures.add(GrammaticalFeatureImpl(
                        name,
                        values.map {
                            val valueName = it["name"] as String
                            GrammaticalFeatureValue(valueName, it["shortName"] as? String ?: valueName)
                        }
                ))
            }
        }
    }

    fun importPartsOfSpeech() {
        val blocks = elements.getOrElse("partsOfSpeech") { emptyList<Any>() }
        for (block in blocks) {
            @Suppress("UNCHECKED_CAST")
            for (featureSrc in block as List<StringAny>) {
                val name = featureSrc["name"] as String
                val shortName = featureSrc["shortName"] as String

                partsOfSpeech.add(PartOfSpeechImpl(name, shortName))
            }
        }
    }
}