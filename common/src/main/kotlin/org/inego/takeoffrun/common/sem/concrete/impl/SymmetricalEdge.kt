package org.inego.takeoffrun.common.sem.concrete.impl

import org.inego.takeoffrun.common.sem.concrete.GraphEdge
import org.inego.takeoffrun.common.sem.concrete.SlotTarget
import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.sem.ontology.impl.SymmetricalRelation

class SymmetricalEdge(override val relation: Relation, private val target: SlotTarget.Symmetrical) : GraphEdge {

    constructor(relation: Relation, target1: Int, target2: Int)
            : this(relation, SlotTarget.Symmetrical(target1, target2))

    override fun slotNode(slot: RelationSlot): SlotTarget.Symmetrical {
        assert(slot == SymmetricalRelation.Slot)
        return target
    }

    override fun toString(): String = "${relation.name} : $target"

    val target1 = target.node1
    val target2 = target.node2
}