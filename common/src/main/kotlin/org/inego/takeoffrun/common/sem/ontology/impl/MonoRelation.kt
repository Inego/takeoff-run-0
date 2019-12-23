package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import java.util.*

class MonoRelation(id: UUID, name: String) : BaseRelation(id, name) {
    override val slots: Collection<RelationSlot>
        get() = listOf(MonoSlot)
}

object MonoSlot : RelationSlot {
    override val id: UUID = UUID.fromString("2c5bc945-39cc-42c2-8b84-2d582953fa01")
    override val name: String = "Mono slot"
}