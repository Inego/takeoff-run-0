package org.inego.takeoffrun.common.sem.ontology


interface Ontology {
    val relations: Collection<Relation>

    fun findByName(name: String): Relation?
}