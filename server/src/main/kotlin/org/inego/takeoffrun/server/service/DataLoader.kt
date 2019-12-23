@file:Suppress("UNCHECKED_CAST")

package org.inego.takeoffrun.server.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.ktor.util.extension
import org.apache.logging.log4j.kotlin.logger
import java.nio.file.FileSystems
import java.nio.file.Files

val YAML_EXTENSIONS = listOf("yaml", "yml")

val log = logger("DataLoader")

fun main() {
    log.info("Starting import...")

    val yamlFactory = YAMLFactory()
    val objectMapper = ObjectMapper(yamlFactory)

    val ontologyPath = FileSystems.getDefault().getPath("data/ontology")

    Files.walk(ontologyPath, 1)
            .filter { it.extension in YAML_EXTENSIONS }
            .forEach {
                log.info("Importing $it")
                val readValue = objectMapper.readValue(it.toFile(), Any::class.java)
                log.info(readValue)
            }

    log.info("Import finished.")
}