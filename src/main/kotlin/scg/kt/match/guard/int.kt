package scg.kt.match.guard

import scg.kt.match.Guard
import scg.kt.match.MatchCase

val MatchCase<Int>.isEven: Guard<Int>
    get() = peek { this % 2 == 0 }

val MatchCase<Int>.isNegative: Guard<Int>
    get() = peek { this < 0 }

val MatchCase<Int>.isPositive: Guard<Int>
    get() = peek { this > 0 }

fun MatchCase<Int>.within(range: IntRange): Guard<Int> = peek { this in range }

