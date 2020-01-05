@file:Suppress("UNCHECKED_CAST")

package org.inego.takeoffrun.server.service

import io.ktor.util.extension
import org.apache.logging.log4j.kotlin.logger
import org.inego.takeoffrun.common.sem.ontology.Ontology
import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.impl.ActionTransitiveRelation
import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.common.sem.ontology.impl.SymmetricalRelation
import org.inego.takeoffrun.server.utils.StringAny
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.FileSystems
import java.nio.file.Files
import org.intellij.lang.annotations.Language as IdeaLang


val YAML_EXTENSIONS = listOf("yaml", "yml")

private val log = logger("DataLoader")

fun main() {
    log.info("Starting import...")

    val defaultFs = FileSystems.getDefault()

    val ontology = loadOntology()

    LanguageLoader.load(defaultFs.getPath("data/languages"))

    log.info("Import finished.")

//    testGenerate(ontology)
    testParseSemGraph(ontology)
}

private fun loadOntology(): Ontology {
    log.info("Loading ontology...")

    val ontologyPath = FileSystems.getDefault().getPath("data/ontology")

    val loadState = OntologyLoadState.new()

    Files.walk(ontologyPath, 1)
            .filter { it.extension in YAML_EXTENSIONS }
            .forEach {
                log.info("Importing $it")
                DataLoader.load(loadState, FileInputStream(it.toFile()))
            }

    val ontology = loadState.ontologyBuilder.build()

    log.info("Ontology loaded.")
    return ontology
}

fun testParseSemGraph(ontology: Ontology) {

    // "I'm listening to a song of my sister"
    @IdeaLang("YAML")
    val yaml = """
        _speaker: 0
        female: 1
        song: 2
        sibling: [0, 1]
        author-of: [1, 2]
        listen-to: [0, 2]
        """.trimIndent()

    val parser = SemGraphYamlParser(ontology)
    val graph = parser.parseYaml(yaml)
    println(graph.print())
}

private fun testGenerate(ontology: Ontology) {
    val semGraphGenerator = SemGraphGenerator(ontology)
    repeat(100) {
        val graph = semGraphGenerator.generateSemGraph(3)
        println(graph.print())
    }
}

object DataLoader {

    fun load(ontologyLoadState: OntologyLoadState, inputStream: InputStream) {
        val readValue = objectMapper.readValue(inputStream, Any::class.java) as StringAny

        val relations = readRelations(readValue["relations"] as StringAny)

        ontologyLoadState.ontologyBuilder.addRelations(relations)
    }

    private fun readRelations(map: StringAny?): List<Relation> {
        if (map == null) {
            return emptyList()
        }

        log.info("Reading relations: $map")

        val result = ArrayList<Relation>()

        // TODO refactor
        result.addAll(readRelationsMono(map["mono"] as List<String>))
        result.addAll(readRelationsSymmetrical(map["symmetrical"] as List<String>))
        result.addAll(readRelationsActionTransitive(map["action-transitive"] as List<String>))

        return result
    }

    private fun readRelationsMono(list: List<String>?): List<MonoRelation> {
        if (list == null) {
            return emptyList()
        }

        log.info("Reading mono relations: $list")

        return list.map { MonoRelation(it) }
    }

    private fun readRelationsSymmetrical(list: List<String>?): List<SymmetricalRelation> {
        if (list == null) {
            return emptyList()
        }

        log.info("Reading symmetrical relations: $list")

        return list.map { SymmetricalRelation(it) }
    }


    private fun readRelationsActionTransitive(list: List<String>?): List<ActionTransitiveRelation> {
        if (list == null) {
            return emptyList()
        }

        log.info("Reading action-transitive relations: $list")

        return list.map { ActionTransitiveRelation(it) }
    }

}