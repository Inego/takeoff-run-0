package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import java.util.*

data class RelationSlotImpl(override val id: UUID, override val name: String) : RelationSlot