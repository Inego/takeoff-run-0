package org.inego.takeoffrun.common.sem.concrete.impl

import org.inego.takeoffrun.common.sem.concrete.Context
import org.inego.takeoffrun.common.sem.concrete.GraphEdge
import org.inego.takeoffrun.common.sem.concrete.SemanticGraph

class SemanticGraphImpl(override val nodeCount: Int, override val edges: Collection<GraphEdge>) : SemanticGraph {
    override val context: Context
        get() = Context.NONE
}