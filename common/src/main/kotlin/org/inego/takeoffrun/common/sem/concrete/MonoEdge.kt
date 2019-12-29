package org.inego.takeoffrun.common.sem.concrete

import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.common.sem.ontology.RelationSlot

class MonoEdge(override val relation: MonoRelation, node: Int) : GraphEdge {
    val target = SlotTarget.Mono(node)

    override fun slotNode(slot: RelationSlot): SlotTarget.Mono {
        assert(slot == MonoRelation.Slot)
        return target
    }

    override fun toString(): String = "${relation.name} : $target"
}