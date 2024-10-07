package scg.kt.match.guard

import scg.kt.match.Guard
import scg.kt.match.MatchCase

val MatchCase<String>.isEmpty: Guard<String>
    get() = peek { isEmpty() }

val MatchCase<String>.isBlank: Guard<String>
    get() = peek { isBlank() }

val MatchCase<String>.isNotEmpty: Guard<String>
    get() = peek { isNotEmpty() }

val MatchCase<String>.isNotBlank: Guard<String>
    get() = peek { isNotBlank() }

fun MatchCase<String>.startsWith(prefix: String): Guard<String> =
    this.peek { startsWith(prefix) }

fun MatchCase<String>.endsWith(suffix: String): Guard<String> =
    this.peek { endsWith(suffix) }

fun MatchCase<String>.all(p: (Char) -> Boolean): Guard<String> =
    this.peek { all(p) }

fun MatchCase<String>.any(p: (Char) -> Boolean): Guard<String> =
    this.peek { any(p) }
