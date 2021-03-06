package org.inego.takeoffrun.common.sem.concrete

import org.inego.takeoffrun.common.sem.concrete.Context

interface SemanticGraph {
    val context: Context
    val nodeCount: Int
    val edges: Collection<GraphEdge>
}