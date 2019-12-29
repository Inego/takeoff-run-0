package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.concrete.SlotTarget
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.utils.hash
import java.util.*

class SymmetricalRelation(id: UUID, name: String) : BaseRelation(id, name) {
    constructor(name: String) : this(hash(name), name)

    override val slots: Collection<RelationSlot> = Slots

    companion object {
        val Slot = RelationSlotImpl(
                "357d73a2-973e-4188-8df7-884a86e1ee9e",
                "Symmetrical",
                SlotTarget.Symmetrical::class
        )
        val Slots = listOf(Slot)
    }
}

