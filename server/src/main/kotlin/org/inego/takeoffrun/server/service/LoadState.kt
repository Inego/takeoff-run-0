package org.inego.takeoffrun.server.service

import org.inego.takeoffrun.server.service.ontology.builder.OntologyBuilder

data class LoadState(
        val ontologyBuilder: OntologyBuilder
) {
    companion object {
        fun new() = LoadState(
                OntologyBuilder()
        )
    }
}