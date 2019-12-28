package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.concrete.SlotTarget
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.utils.hash
import java.util.*

class MonoRelation(id: UUID, name: String) : BaseRelation(id, name) {

    constructor(name: String) : this(hash(name), name)

    override val slots: Collection<RelationSlot> = Slots

    companion object {
        val Slot = RelationSlotImpl(
                "2c5bc945-39cc-42c2-8b84-2d582953fa01",
                "Slot",
                SlotTarget.Mono::class
        )
        val Slots = listOf(SymmetricalRelation.Slot)
    }
}
