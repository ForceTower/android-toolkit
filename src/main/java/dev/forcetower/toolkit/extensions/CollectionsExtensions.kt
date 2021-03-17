package dev.forcetower.toolkit.extensions

import kotlin.math.min

fun <T> List<T>.limit(limit: Int): List<T> {
    return this.subList(0, min(limit, size))
}