package org.inego.takeoffrun.server.service.fileload.builder

import org.inego.takeoffrun.common.sem.ontology.Ontology
import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.common.sem.ontology.impl.OntologyImpl

class OntologyBuilder {
    private val relations = ArrayList<Relation>()

    init {
        relations.add(MonoRelation("_speaker"))
    }

    fun addRelations(newRelations: Collection<Relation>) {
        relations.addAll(newRelations)
    }

    fun build(): Ontology {
        return OntologyImpl(relations.toList())
    }
}