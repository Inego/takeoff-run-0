package org.inego.takeoffrun.common.sem.concrete.impl

import org.inego.takeoffrun.common.sem.concrete.GraphEdge
import org.inego.takeoffrun.common.sem.concrete.SlotTarget
import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.RelationSlot

class GraphEdgeImpl(override val relation: Relation, private val slotTargets: Map<RelationSlot, SlotTarget>) : GraphEdge {

    override fun slotNode(slot: RelationSlot): SlotTarget {
        return slotTargets[slot] ?: error("$slot not found")
    }

    override fun toString(): String = "${relation.name} : $slotTargets"
}