package scg.kt.match

import kotlin.coroutines.startCoroutine

operator fun <A, Y> Partial<A, Y>.invoke(argument: A): Y = match(argument, this)

fun <A, Y> match(argument: A, partial: Partial<A, Y>): Y =
    YieldScope<A, Y>(argument)
        .also { partial.startCoroutine(it, it) }
        .yield()

fun <A, Y> Partial<A, Y>.definedAt(argument: A): Boolean =
    CanMatchScope<A, Y>(argument)
        .also { startCoroutine(it, it) }
        .canMatch()

