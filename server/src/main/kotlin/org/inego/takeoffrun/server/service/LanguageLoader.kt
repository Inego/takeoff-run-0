package org.inego.takeoffrun.server.service

import org.apache.logging.log4j.kotlin.logger
import org.inego.takeoffrun.common.language.LanguageBase
import org.inego.takeoffrun.common.language.impl.LanguageImpl
import org.inego.takeoffrun.server.utils.StringAny
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
            log.info("Loading base for $language finished.")
        }

        log.info("Languages loaded.")
        // TODO
        return emptyList()
    }
}