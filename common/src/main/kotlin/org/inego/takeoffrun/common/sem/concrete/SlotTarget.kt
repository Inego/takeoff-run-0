package org.inego.takeoffrun.common.sem.concrete

sealed class SlotTarget {
    abstract val targets: Collection<Int>

    class Mono(val node: Int) : SlotTarget() {
        override val targets: Collection<Int> by lazy { listOf(node) }
    }

    class Multi(override val targets: Collection<Int>) : SlotTarget()
}



