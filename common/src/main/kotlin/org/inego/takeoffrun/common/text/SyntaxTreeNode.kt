package org.inego.takeoffrun.common.text

interface SyntaxTreeNode {
    val children: List<SyntaxTreeNode>
}