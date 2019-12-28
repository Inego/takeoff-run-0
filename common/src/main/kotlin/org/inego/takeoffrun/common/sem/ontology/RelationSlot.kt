package org.inego.takeoffrun.common.sem.ontology

import org.inego.takeoffrun.common.sem.concrete.SlotTarget
import java.util.*
import kotlin.reflect.KClass

interface RelationSlot {
    val id: UUID
    val name: String

    val targetType: KClass<out SlotTarget>;
}