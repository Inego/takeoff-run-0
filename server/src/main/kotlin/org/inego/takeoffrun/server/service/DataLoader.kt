@file:Suppress("UNCHECKED_CAST")

package org.inego.takeoffrun.server.service

import io.ktor.util.extension
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.snakeyaml.engine.v2.api.Load
import org.snakeyaml.engine.v2.api.LoadSettings
import java.io.FileInputStream
import java.nio.file.FileSystems
import java.nio.file.Files
import org.apache.logging.log4j.kotlin.logger

val YAML_EXTENSIONS = listOf("yaml", "yml")

val logger = logger("Data loader")

fun main() {

    LogManager.getRootLogger()

    logger.info("123")

    val loadSettings = LoadSettings.builder().build()

    val load = Load(loadSettings)


    val ontologyPath = FileSystems.getDefault().getPath("data/ontology")

    Files.walk(ontologyPath, 1)
            .filter { it.extension in YAML_EXTENSIONS }
            .forEach {
//                println(it)
                val loaded = load.loadFromInputStream(FileInputStream(it.toFile())) as Map<String, Any>
                val relations = loaded["relations"]

            }

}