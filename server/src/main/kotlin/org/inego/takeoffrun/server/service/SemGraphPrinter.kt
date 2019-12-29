package org.inego.takeoffrun.server.service

import org.inego.takeoffrun.common.sem.concrete.MonoEdge
import org.inego.takeoffrun.common.sem.concrete.SemanticGraph
import org.inego.takeoffrun.common.sem.concrete.SymmetricalEdge
import org.inego.takeoffrun.common.sem.ontology.Relation
import java.lang.StringBuilder

fun SemanticGraph.print(): String {

    val edgeStrings = arrayListOf<String>()

    val introducedNodes = hashSetOf<Int>()

    val nodeMonoProps = Array<ArrayList<Relation>>(nodeCount) { arrayListOf() }



    // First pass: gather monos

    for (edge in edges) {
        if (edge is MonoEdge) {
            nodeMonoProps[edge.target.node].add(edge.relation)
        }
    }

    // Second pass:

    val currentString = StringBuilder()
    var previousNode = -1

    for (edge in edges) {
        when (edge) {
            is SymmetricalEdge -> {


            }
        }
    }

    // Third pass: list not introduced

    return "[$nodeCount] " + edgeStrings.joinToString(separator = "; ")
}

fun renderBiRelation(relation: Relation, stringBuilder: StringBuilder) {

}

fun renderNode(idx: Int, introducedNodes: HashSet<Int>, nodeMonoProps: Array<ArrayList<Relation>>): String {
    if (idx in introducedNodes) {
        return "$idx"
    }
    introducedNodes.add(idx)
    val relNames = nodeMonoProps[idx].joinToString(", ")
    return "$idx ($relNames)"
}