package org.inego.takeoffrun.server.service

import org.inego.takeoffrun.server.service.ontology.builder.OntologyBuilder

data class OntologyLoadState(
        val ontologyBuilder: OntologyBuilder
) {
    companion object {
        fun new() = OntologyLoadState(
                OntologyBuilder()
        )
    }
}