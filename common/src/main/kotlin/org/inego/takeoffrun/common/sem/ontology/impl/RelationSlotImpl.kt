package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.concrete.SlotTarget
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import java.util.*
import kotlin.reflect.KClass

data class RelationSlotImpl(
        override val id: UUID,
        override val name: String,
        override val targetType: KClass<out SlotTarget>
) : RelationSlot {

    constructor(uuidString: String, name: String, targetType: KClass<out SlotTarget>) : this(
            UUID.fromString(uuidString),
            name,
            targetType
    )
}
