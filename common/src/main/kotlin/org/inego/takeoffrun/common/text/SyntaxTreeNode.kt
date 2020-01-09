package org.inego.takeoffrun.common.text

import org.inego.takeoffrun.common.language.SyntaxTreeNodeType

interface SyntaxTreeNode {
    val children: List<SyntaxTreeNode>
    val nodeType: SyntaxTreeNodeType
    val mainChild: SyntaxTreeNode?
}