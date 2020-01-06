package org.inego.takeoffrun.server.service.fileload

import org.inego.takeoffrun.server.service.fileload.builder.OntologyBuilder

data class OntologyLoadState(
        val ontologyBuilder: OntologyBuilder
) {
    companion object {
        fun new() = OntologyLoadState(
                OntologyBuilder()
        )
    }
}