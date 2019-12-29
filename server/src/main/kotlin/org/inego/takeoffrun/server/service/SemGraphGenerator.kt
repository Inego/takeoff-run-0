package org.inego.takeoffrun.server.service

import org.inego.takeoffrun.common.sem.concrete.*
import org.inego.takeoffrun.common.sem.concrete.impl.SemanticGraphImpl
import org.inego.takeoffrun.common.sem.ontology.Ontology
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.server.utils.listOfRandomFrom
import kotlin.random.Random

class SemGraphGenerator(private val ontology: Ontology) {

    val rnd = Random.Default

    fun generateSemGraph(nodeCount: Int): SemanticGraph {

        if (nodeCount < 2) {
            throw AssertionError("Too few nodes")
        }

        val nodeCountRange = 0 until nodeCount;

        val ontologyRelations = ontology.relations

        val graphEdges = ArrayList<GraphEdge>()

        // Randomly add edges until all nodes belong to the 0 cluster,
        // that is, its size is equal to nodeCount

        val clusters = ArrayList<HashSet<Int>>(nodeCount)

        val nodeClusters = IntArray(nodeCount) { -1 }

        while (clusters.isEmpty() || clusters[0].size < nodeCount) {
            val relation = ontologyRelations.random()

            val newEdge: GraphEdge

            if (relation is MonoRelation) {
                newEdge = MonoEdge(relation, nodeCountRange.random())
            }
            else {
                val slotTargetMap = hashMapOf<RelationSlot, SlotTarget>()

                val affectedNodes = hashSetOf<Int>()

                for (slot in relation.slots) {
                    // Randomly assign

                    val slotTarget =  when (slot.targetType) {
                        SlotTarget.Mono::class ->  SlotTarget.Mono(rnd.nextInt(nodeCount))
                        SlotTarget.Symmetrical::class -> SlotTarget.Symmetrical(rnd.nextInt(nodeCount), rnd.nextInt(nodeCount))
                        else ->  SlotTarget.Multi(listOfRandomFrom(nodeCountRange, rnd.nextInt(2, 3)))
                    }

                    slotTargetMap[slot] = slotTarget
                    affectedNodes.addAll(slotTarget.targets)
                }

                // First pass:
                // For all nodes in the slot target, determine the smallest cluster number (if any)

                var minCluster: Int? = null

                for (target in affectedNodes) {
                    val cluster = nodeClusters[target]
                    if (cluster > -1) {
                        if (minCluster == null || minCluster > cluster) {
                            minCluster = cluster
                        }
                    }
                }

                val targetCluster: HashSet<Int>

                if (minCluster == null) {
                    // Create a cluster for nodes of this edge
                    minCluster = clusters.size
                    targetCluster = hashSetOf()
                    clusters.add(targetCluster)
                } else {
                    targetCluster = clusters[minCluster]
                }

                // Second pass
                // Assign or transfer all nodes which are not yet contained in the cluster, to this cluster.

                for (target in affectedNodes) {
                    val cluster = nodeClusters[target]
                    if (cluster == -1) {
                        nodeClusters[target] = minCluster
                        targetCluster.add(target)
                    }
                    else if (cluster != minCluster) {
                        val oldCluster = clusters[cluster]
                        for (oldClusterItem in oldCluster) {
                            nodeClusters[oldClusterItem] = minCluster
                            targetCluster.add(oldClusterItem)
                        }
                    }
                }

                newEdge = GraphEdgeImpl(relation, slotTargetMap)
            }

            graphEdges.add(newEdge)
        }

        return SemanticGraphImpl(nodeCount, graphEdges)
    }
}
