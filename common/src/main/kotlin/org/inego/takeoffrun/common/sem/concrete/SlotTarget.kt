package org.inego.takeoffrun.common.sem.concrete

sealed class SlotTarget {
    abstract val targets: Collection<Int>

    class Mono(private val node: Int) : SlotTarget() {
        override val targets: Collection<Int> by lazy { listOf(node) }
        override fun toString(): String = node.toString()
    }

    class Symmetrical(private val node1: Int, private val node2: Int) : SlotTarget() {
        override val targets: Collection<Int> by lazy { listOf(node1, node2) }
        override fun toString(): String = "$node1, $node2"
    }

    class Multi(override val targets: Collection<Int>) : SlotTarget() {
        override fun toString(): String = targets.toString()
    }
}



