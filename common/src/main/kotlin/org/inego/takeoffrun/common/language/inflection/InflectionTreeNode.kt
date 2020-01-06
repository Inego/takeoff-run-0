package org.inego.takeoffrun.common.language.inflection

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue

interface InflectionTreeNode {
    fun getString(): String?
    fun down(featureValues: Collection<GrammaticalFeatureValue>): InflectionTreeNode
}

class InflectionTreeLeaf(private val value: String) : InflectionTreeNode {
    override fun getString() = value

    override fun down(featureValues: Collection<GrammaticalFeatureValue>): InflectionTreeNode {
        error("Cannot go down, this node is a leaf")
    }
}
