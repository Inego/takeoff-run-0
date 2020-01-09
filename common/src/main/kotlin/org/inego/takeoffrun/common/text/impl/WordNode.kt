package org.inego.takeoffrun.common.text.impl

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue
import org.inego.takeoffrun.common.language.SyntaxTreeNodeType
import org.inego.takeoffrun.common.language.Word
import org.inego.takeoffrun.common.text.SyntaxTreeNode

class WordNode(val word: Word, override val nodeType: SyntaxTreeNodeType) : SyntaxTreeNode {
    val externalFeatures = arrayListOf<GrammaticalFeatureValue>()
    override val children: List<SyntaxTreeNode>
        get() = emptyList()
    override val mainChild: SyntaxTreeNode?
        get() = null
}