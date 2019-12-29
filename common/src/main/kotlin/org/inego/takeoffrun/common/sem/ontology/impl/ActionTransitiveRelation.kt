package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.concrete.SlotTarget
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.utils.hash
import java.util.*

class ActionTransitiveRelation(id: UUID, name: String) : BaseRelation(id, name) {
    constructor(name: String) : this(hash(name), name)

    override val slots: Collection<RelationSlot> = Slots

    companion object {
        val SubjectSlot = RelationSlotImpl(
                "a4fd503b-ca6e-4b73-9edf-5de3dab897e5",
                "Subject",
                SlotTarget.Mono::class
        )
        val ObjectSlot = RelationSlotImpl(
                "f599067f-9c2f-4716-94ef-43cd42023583",
                "Object",
                SlotTarget.Mono::class
        )
        val Slots = listOf(SubjectSlot, ObjectSlot)
    }
}

