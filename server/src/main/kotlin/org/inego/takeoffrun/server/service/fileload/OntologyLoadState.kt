package org.inego.takeoffrun.server.service.fileload

import org.inego.takeoffrun.server.service.fileload.ontology.builder.OntologyBuilder

data class OntologyLoadState(
        val ontologyBuilder: OntologyBuilder
) {
    companion object {
        fun new() = OntologyLoadState(
                OntologyBuilder()
        )
    }
}