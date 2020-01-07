package org.inego.takeoffrun.server.service.fileload.language

import io.ktor.util.extension
import org.apache.logging.log4j.kotlin.logger
import org.inego.takeoffrun.common.language.*
import org.inego.takeoffrun.common.language.impl.GrammaticalFeatureImpl
import org.inego.takeoffrun.common.language.impl.LanguageImpl
import org.inego.takeoffrun.common.language.impl.PartOfSpeechImpl
import org.inego.takeoffrun.common.language.inflection.InflectionTreeLeaf
import org.inego.takeoffrun.common.language.inflection.InflectionTreeNode
import org.inego.takeoffrun.server.service.fileload.YAML_EXTENSIONS
import org.inego.takeoffrun.server.service.fileload.objectMapper
import org.inego.takeoffrun.server.utils.StringAny
import java.nio.file.Files
import java.nio.file.Path

private val log = logger("LanguageLoader")


object LanguageLoader {

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
        loadState.importDictionary()

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

class PartOfSpeechDictionary {
    val templates = arrayListOf<InflectionTreeNode>()
    val words = arrayListOf<Word>()
    val templatesRaw = hashMapOf<String, Any>()
    val wordsRaw = hashMapOf<String, Any>()
}

class LanguageLoadState {
    private val elements = hashMapOf<String, ArrayList<Any>>()

    private val grammaticalFeatures = arrayListOf<GrammaticalFeature>()
    private val partsOfSpeech = arrayListOf<PartOfSpeech>()
    private val partsOfSpeechShortNames = hashMapOf<String, PartOfSpeech>()

    private val dictionary = hashMapOf<PartOfSpeech, PartOfSpeechDictionary>()

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

        partsOfSpeech.associateByTo(partsOfSpeechShortNames) { it.shortName }
        partsOfSpeech.associateWithTo(dictionary) { PartOfSpeechDictionary() }
    }


    fun parseInflectionTreeNode(yaml: Any): InflectionTreeNode {
        if (yaml is String) {
            return InflectionTreeLeaf(yaml)
        }
        @Suppress("UNCHECKED_CAST")
        for ((key, value) in yaml as StringAny) {

        }
        TODO()
    }

    fun importDictionary() {
        val blocks = elements.getOrElse("dictionary") { emptyList<Any>() }
        for (block in blocks) {
            @Suppress("UNCHECKED_CAST")
            for ((posShortName, posData) in block as StringAny) {
                posData as StringAny
                val partOfSpeech = partsOfSpeechShortNames[posShortName]
                if (partOfSpeech == null) {
                    log.error("Cannot find part of speech $posShortName")
                    continue
                }
                val partOfSpeechDictionary = dictionary[partOfSpeech]!!

                posData["words"] ?.let { partOfSpeechDictionary.wordsRaw.putAll(it as StringAny) }
                posData["templates"] ?.let { partOfSpeechDictionary.templatesRaw.putAll(it as StringAny) }
            }
        }
    }
}