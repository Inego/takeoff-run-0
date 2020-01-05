package org.inego.takeoffrun.common.text.impl

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue
import org.inego.takeoffrun.common.language.Word
import org.inego.takeoffrun.common.text.SyntaxTreeNode

class WordNode(val word: Word) : SyntaxTreeNode {
    val externalFeatures = arrayListOf<GrammaticalFeatureValue>()
    override val children: List<SyntaxTreeNode>
        get() = emptyList()

}