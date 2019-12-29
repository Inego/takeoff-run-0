package org.inego.takeoffrun.common.sem.concrete

import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.sem.ontology.impl.SymmetricalRelation

class SymmetricalEdge(override val relation: Relation, private val target: SlotTarget.Symmetrical) : GraphEdge {

    override fun slotNode(slot: RelationSlot): SlotTarget.Symmetrical {
        assert(slot == SymmetricalRelation.Slot)
        return target
    }

    override fun toString(): String = "${relation.name} : $target"

    val target1 = target.node1
    val target2 = target.node2
}