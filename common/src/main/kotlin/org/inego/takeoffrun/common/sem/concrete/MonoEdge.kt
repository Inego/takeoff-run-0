package org.inego.takeoffrun.common.sem.concrete

import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.common.sem.ontology.impl.MonoSlot
import org.inego.takeoffrun.common.sem.ontology.RelationSlot

class MonoEdge(override val relation: MonoRelation, private val node: Int) : GraphEdge {

    override fun slotNode(slot: RelationSlot): Int {
        assert(slot == MonoSlot)
        return node
    }
}