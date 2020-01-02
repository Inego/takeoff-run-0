package org.inego.takeoffrun.server.utils


fun toMaybePairOfInts(value: Any): Pair<Int, Int>? {
    if (value is List<*> && value.size == 2 && value[0] is Int && value[1] is Int) {
        @Suppress("UNCHECKED_CAST")
        return Pair(value[0] as Int, value[1] as Int)
    }

    return null
}