package org.inego.takeoffrun.common.sem.ontology.impl

import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.utils.hash
import java.util.*

abstract class BaseRelation(override val id: UUID, override val name: String) : Relation {
    constructor(name: String) : this(hash(name), name)
}