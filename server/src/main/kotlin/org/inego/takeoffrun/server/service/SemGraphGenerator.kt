package org.inego.takeoffrun.server.service

import org.inego.takeoffrun.common.sem.concrete.*
import org.inego.takeoffrun.common.sem.concrete.impl.SemanticGraphImpl
import org.inego.takeoffrun.common.sem.ontology.Ontology
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.common.sem.ontology.impl.SymmetricalRelation
import org.inego.takeoffrun.server.utils.listOfRandomFrom
import org.inego.takeoffrun.server.utils.randomSmallSublist
import kotlin.random.Random

class SemGraphGenerator(ontology: Ontology) {

    private val rnd = Random.Default

    private val ontologyRelations = ontology.relations

    private val monoRelations = ontologyRelations.filterIsInstance<MonoRelation>()
    private val otherRelations = ontologyRelations.filterNot { it is MonoRelation }

    fun generateSemGraph(nodeCount: Int): SemanticGraph {

        if (nodeCount < 2) {
            throw AssertionError("Too few nodes")
        }

        val nodeCountRange = 0 until nodeCount
        val nodeList = nodeCountRange.toList()

        val graphEdges = ArrayList<GraphEdge>()

        val clusters = ArrayList<HashSet<Int>>(nodeCount)

        // First, assign every node some mono relations
        for (node in nodeCountRange) {
            monoRelations.randomSmallSublist((1..2).random()).mapTo(graphEdges) {  MonoEdge(it, node) }
        }

        // Randomly add non-mono edges until all nodes belong to the 0 cluster,
        // that is, its size is equal to nodeCount

        val nodeClusters = IntArray(nodeCount) { -1 }

        while (clusters.isEmpty() || clusters[0].size < nodeCount) {
            val relation = otherRelations.random()

            val newEdge: GraphEdge

            when (relation) {
                is MonoRelation -> {
                    error("Mono relation")
                }
                is SymmetricalRelation -> {
                    val target = generateSymmetricalTarget(nodeCount, nodeList)
                    newEdge = SymmetricalEdge(relation, target)
                    processClusters(target.targets.toSet(), nodeClusters, clusters)
                }
                else -> {
                    val slotTargetMap = hashMapOf<RelationSlot, SlotTarget>()

                    val affectedNodes = hashSetOf<Int>()

                    for (slot in relation.slots) {
                        // Randomly assign

                        val slotTarget =  when (slot.targetType) {
                            SlotTarget.Mono::class ->  SlotTarget.Mono(rnd.nextInt(nodeCount))
                            SlotTarget.Symmetrical::class -> generateSymmetricalTarget(nodeCount, nodeList)
                            else ->  SlotTarget.Multi(listOfRandomFrom(nodeCountRange, rnd.nextInt(2, 3)))
                        }

                        slotTargetMap[slot] = slotTarget
                        affectedNodes.addAll(slotTarget.targets)
                    }

                    newEdge = GraphEdgeImpl(relation, slotTargetMap)
                    processClusters(affectedNodes, nodeClusters, clusters)
                }
            }

            graphEdges.add(newEdge)
        }

        return SemanticGraphImpl(nodeCount, graphEdges)
    }

    private fun generateSymmetricalTarget(nodeCount: Int, nodeList: List<Int>): SlotTarget.Symmetrical {
        val sublist = nodeList.randomSmallSublist(nodeCount)
        return SlotTarget.Symmetrical(sublist[0], sublist[1])
    }

    private fun processClusters(affectedNodes: Set<Int>, nodeClusters: IntArray, clusters: ArrayList<HashSet<Int>>) {

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
            } else if (cluster != minCluster) {
                val oldCluster = clusters[cluster]
                for (oldClusterItem in oldCluster) {
                    nodeClusters[oldClusterItem] = minCluster
                    targetCluster.add(oldClusterItem)
                }
            }
        }
    }
}
