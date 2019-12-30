package org.inego.takeoffrun.server.utils

fun <E> randomSublist(src: Iterable<E>, size: Int): List<E> {
    // TODO optimize for small subsets collections
    return src.shuffled().take(size)
}

fun <E> randomSmallSublist(src: List<E>, size: Int): List<E> {
    if (size == 1) {
        return listOf(src.random())
    }

    val result = mutableListOf<E>()
    while (result.size < size) {
        val next = src.random()
        if (next !in result) {
            result.add(next)
        }
    }

    return result
}

fun listOfRandomFrom(range: IntRange, size: Int): List<Int> {
    return sequence<Int> { range.random() }.take(size).toList()
}
