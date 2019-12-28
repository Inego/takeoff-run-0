package org.inego.takeoffrun.server.service

import org.inego.takeoffrun.common.sem.concrete.SemanticGraph
import org.inego.takeoffrun.common.sem.ontology.Ontology

class SemGraphGenerator(private val ontology: Ontology) {

    fun generateSemGraph(nodeCount: Int): SemanticGraph {

        // Randomly add edges until all nodes belong to the 0 cluster,
        // that is, its size is equal to nodeCount

        val clusters = HashSet<>




        throw TODO()
    }

}