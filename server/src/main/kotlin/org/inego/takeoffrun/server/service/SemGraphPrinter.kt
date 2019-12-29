package org.inego.takeoffrun.server.service

import org.inego.takeoffrun.common.sem.concrete.*
import org.inego.takeoffrun.common.sem.ontology.Relation
import java.lang.StringBuilder

private class SemGraphPrinterState(val semanticGraph: SemanticGraph) {
    val edgeStrings = arrayListOf<String>()

    val introducedNodes = hashSetOf<Int>()

    val nodeMonoProps = Array<ArrayList<Relation>>(semanticGraph.nodeCount) { arrayListOf() }

    val currentString = StringBuilder()

    var previousNode = -1

    fun print(): String {
        // First pass: gather monos

        for (edge in semanticGraph.edges) {
            if (edge is MonoEdge) {
                nodeMonoProps[edge.target.node].add(edge.relation)
            }
        }

        // Second pass:
        for (edge in semanticGraph.edges) {
            when (edge) {
                is SymmetricalEdge -> {
                    renderBiRelation(edge, edge.target1, edge.target2, true)
                }
                is MonoEdge -> {}
                else -> {
                    val slots = edge.relation.slots
                    if (slots.size == 2 && slots[0].isMono && slots[1].isMono) {
                        renderBiRelation(edge, edge.monoSlotTarget(0), edge.monoSlotTarget(1), false)
                    } else {



                        previousNode = -1

                        if (currentString.isNotEmpty()) {
                            edgeStrings.add(currentString.toString())
                            currentString.clear()
                        }

                        TODO("Not implemented")
                    }
                }
            }
        }

        // Third pass: list not introduced

        return "[${semanticGraph.nodeCount}] " + edgeStrings.joinToString(separator = "; ")
    }

    fun renderBiRelation(edge: GraphEdge, node1: Int, node2: Int, interchangeable: Boolean) {
        val swap = interchangeable && node2 == previousNode;

        val eff1 = if (swap) node2 else node1;
        val eff2 = if (swap) node1 else node2;

        if (eff1 != previousNode) {
            // Add previous and start from scratch
            if (currentString.isNotEmpty()) {
                edgeStrings.add(currentString.toString())
                currentString.clear()
                currentString.append(renderNode(eff1))
            }
        }

        currentString
                .append(" ")
                .append(edge.relation.name)
                .append(" ")
                .append(renderNode(eff2))

        previousNode = eff2
        // Expand previous (done below)
    }

    fun renderNode(idx: Int): String {
        if (idx in introducedNodes) {
            return "$idx"
        }
        introducedNodes.add(idx)
        val relNames = nodeMonoProps[idx].joinToString(", ")
        return "$idx ($relNames)"
    }
}

fun SemanticGraph.print(): String {
    val printerState = SemGraphPrinterState(this)
    return printerState.print()
}




