package org.inego.takeoffrun.server.utils

fun <E> randomSublist(src: Iterable<E>, size: Int): List<E> {
    // TODO optimize for small subsets collections
    return src.shuffled().take(size)
}

fun listOfRandomFrom(range: IntRange, size: Int): List<Int> {
    return sequence<Int> { range.random() }.take(size).toList()
}
