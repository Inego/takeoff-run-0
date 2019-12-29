package org.inego.takeoffrun.common.sem.concrete

sealed class SlotTarget {
    abstract val targets: Collection<Int>

    class Mono(val node: Int) : SlotTarget() {
        override val targets: Collection<Int> by lazy { listOf(node) }
        override fun toString(): String = node.toString()
    }

    class Symmetrical(val node1: Int, val node2: Int) : SlotTarget() {
        override val targets: Collection<Int> by lazy { listOf(node1, node2) }
        override fun toString(): String = "$node1, $node2"
    }

    class Multi(override val targets: Collection<Int>) : SlotTarget() {
        override fun toString(): String = targets.toString()
    }
}



