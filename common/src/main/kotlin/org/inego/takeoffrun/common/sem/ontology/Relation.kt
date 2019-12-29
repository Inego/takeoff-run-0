package org.inego.takeoffrun.common.sem.ontology

import java.util.*

interface Relation {
    val id: UUID
    val name: String
    val slots: List<RelationSlot>
}