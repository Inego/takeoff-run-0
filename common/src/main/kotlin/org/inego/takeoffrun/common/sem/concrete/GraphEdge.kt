package org.inego.takeoffrun.common.sem.concrete

import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.RelationSlot

interface GraphEdge {
    val relation: Relation

    fun slotNode(slot: RelationSlot): SlotTarget

    fun monoSlotTarget(slotIdx: Int): Int {
        val monoSlot = relation.slots[slotIdx]
        val target = slotNode(monoSlot) as SlotTarget.Mono
        return target.node
    }
}