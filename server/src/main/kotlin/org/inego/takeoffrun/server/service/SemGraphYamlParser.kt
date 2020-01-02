package org.inego.takeoffrun.server.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.inego.takeoffrun.common.sem.concrete.SemanticGraph
import org.inego.takeoffrun.common.sem.ontology.Ontology
import org.inego.takeoffrun.server.utils.StringAny

class SemGraphYamlParser(private val ontology: Ontology) {

    private val yamlFactory = YAMLFactory()
    private val objectMapper = ObjectMapper(yamlFactory)

    fun parseYaml(yaml: String): SemanticGraph {

        @Suppress("UNCHECKED_CAST")
        val readValue = objectMapper.readValue(yaml, Any::class.java) as StringAny

    }

}