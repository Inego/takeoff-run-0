package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.ontology.Ontology
import org.inego.takeoffrun.common.sem.ontology.Relation

class OntologyImpl(override val relations: Collection<Relation>) : Ontology {

    private val relationIndex = relations.associateBy { it.name }

    override fun findByName(name: String) = relationIndex[name]
}