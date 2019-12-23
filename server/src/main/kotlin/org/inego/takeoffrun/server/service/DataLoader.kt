@file:Suppress("UNCHECKED_CAST")

package org.inego.takeoffrun.server.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.ktor.util.extension
import org.apache.logging.log4j.kotlin.logger
import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.server.utils.hash
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.FileSystems
import java.nio.file.Files

typealias StringAny = Map<String, Any>

val YAML_EXTENSIONS = listOf("yaml", "yml")

val log = logger("DataLoader")

fun main() {
    log.info("Starting import...")

    val ontologyPath = FileSystems.getDefault().getPath("data/ontology")

    val loadState = LoadState.new()

    Files.walk(ontologyPath, 1)
            .filter { it.extension in YAML_EXTENSIONS }
            .forEach {
                log.info("Importing $it")
                DataLoader.load(loadState, FileInputStream(it.toFile()))
            }

    val ontology = loadState.ontologyBuilder.build()

    log.info("Import finished.")
}

object DataLoader {
    private val yamlFactory = YAMLFactory()
    private val objectMapper = ObjectMapper(yamlFactory)

    fun load(loadState: LoadState, inputStream: InputStream) {
        val readValue = objectMapper.readValue(inputStream, Any::class.java) as StringAny

        val relations = readRelations(readValue["relations"] as StringAny)

        loadState.ontologyBuilder.addRelations(relations)
    }

    private fun readRelations(map: StringAny?): List<Relation> {
        if (map == null) {
            return emptyList()
        }

        log.info("Reading relations: $map")

        val result = ArrayList<Relation>()

        val monoRelations = readRelationsMono(map["mono"] as List<String>)

        result.addAll(monoRelations)

        return result
    }

    private fun readRelationsMono(list: List<String>?): List<MonoRelation> {
        if (list == null) {
            return emptyList()
        }

        log.info("Reading mono relations: $list")

        return list.map { MonoRelation(hash(it), it) }
    }

}