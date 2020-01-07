package org.inego.takeoffrun.common.language.inflection

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue

interface InflectionTreeNode {
    fun getString(): String?
    fun down(featureValues: Collection<GrammaticalFeatureValue>): InflectionTreeNode
}

inline class InflectionTreeLeaf(private val value: String) : InflectionTreeNode {
    override fun getString() = value

    override fun down(featureValues: Collection<GrammaticalFeatureValue>): InflectionTreeNode {
        error("Cannot go down, this node is a leaf")
    }
}

class InflectionTreeBranch(
        private val map: Map<GrammaticalFeatureValue, InflectionTreeNode>
) : InflectionTreeNode {
    override fun getString(): String? = null

    override fun down(featureValues: Collection<GrammaticalFeatureValue>): InflectionTreeNode {
        for ((key, value) in map.entries) {
            if (key in featureValues) {
                return value
            }
        }
        error("Cannot inflect")
    }
}