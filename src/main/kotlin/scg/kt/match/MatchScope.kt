package scg.kt.match

import kotlin.coroutines.*

typealias Partial<A, Y> = suspend MatchScope<A, Y>.() -> Unit

typealias Yield<A, Y> = (A) -> Y

typealias Guard<A> = (A) -> Boolean

typealias Predicate<A> = java.util.function.Predicate<A>

typealias MatchCase<A> = MatchScope<A, *>

@RestrictsSuspension
sealed interface MatchScope<A, Y> : Guards<A> {

    suspend fun accept(guard: Guard<A>, yield: Yield<A, Y>)

    suspend infix fun A.yield(result: Y) = yieldBy { result }

    suspend infix fun A.yieldBy(yield: (A) -> Y) = accept({ this == it }, yield)

    suspend infix fun A.yieldWith(yield: A.() -> Y) = yieldBy(yield)


    suspend infix fun Guard<A>.yield(result: Y) = yieldBy { result }

    suspend infix fun Guard<A>.yieldBy(yield: (A) -> Y) = accept(this, yield)

    suspend infix fun Guard<A>.yieldWith(yield: A.() -> Y) = yieldBy(yield)


    suspend infix fun Predicate<A>.yield(result: Y) = yieldBy { result }

    suspend infix fun Predicate<A>.yieldBy(yield: (A) -> Y) = accept(this::test, yield)

    suspend infix fun Predicate<A>.yieldWith(yield: A.() -> Y) = yieldBy(yield)


    suspend infix fun Boolean.yield(result: Y) = yieldBy { result }

    suspend infix fun Boolean.yieldBy(yield: (A) -> Y) = accept({ this }, yield)

    suspend infix fun Boolean.yieldWith(yield: A.() -> Y) = yieldBy(yield)

}

interface Guards<A> {

    val any: Guard<A>
        get() = alwaysTrue.value

    fun peek(predicate: A.() -> Boolean): Guard<A> = { predicate(it) }

    operator fun A.not(): Guard<A> = { this != it }

    infix fun A.or(other: A): Guard<A> = this / other

    operator fun A.div(other: A): Guard<A> = { this == it || other == it }

    infix fun A.xor(other: A): Guard<A> = { (this == it) xor (other == it) }

    infix fun A.nor(other: A): Guard<A> = { !((this == it) || (other == it)) }

    infix fun A.nand(other: A): Guard<A> = { !((this == it) && (other == it)) }


    infix fun A.or(g: Guard<A>): Guard<A> = { this == it || g(it) }

    infix fun A.and(g: Guard<A>): Guard<A> = { this == it && g(it) }

    infix fun A.xor(g: Guard<A>): Guard<A> = { (this == it) xor g(it) }

    infix fun A.nor(g: Guard<A>): Guard<A> = { !(this == it || g(it)) }

    infix fun A.nand(g: Guard<A>): Guard<A> = { !(this == it && g(it)) }


    infix fun A.or(p: Predicate<A>): Guard<A> = { this == it || p.test(it) }

    infix fun A.and(p: Predicate<A>): Guard<A> = { this == it && p.test(it) }

    infix fun A.xor(p: Predicate<A>): Guard<A> = { (this == it) xor p.test(it) }

    infix fun A.nor(p: Predicate<A>): Guard<A> = { !(this == it || p.test(it)) }

    infix fun A.nand(p: Predicate<A>): Guard<A> = { !(this == it && p.test(it)) }


    infix fun A.or(condition: Boolean): Guard<A> = { this == it || condition }

    infix fun A.and(condition: Boolean): Guard<A> = { this == it && condition }

    infix fun A.xor(condition: Boolean): Guard<A> = { (this == it) xor condition }

    infix fun A.nor(condition: Boolean): Guard<A> = { !(this == it || condition) }

    infix fun A.nand(condition: Boolean): Guard<A> = { !(this == it && condition) }


    operator fun Guard<A>.not(): Guard<A> = { !invoke(it) }

    operator fun Guard<A>.div(other: A): Guard<A> = { invoke(it) || other == it }

    infix fun Guard<A>.or(other: A): Guard<A> = { invoke(it) || other == it }

    infix fun Guard<A>.and(other: A): Guard<A> = { invoke(it) && other == it }

    infix fun Guard<A>.xor(other: A): Guard<A> = { invoke(it) xor other == it }

    infix fun Guard<A>.nor(other: A): Guard<A> = { !(invoke(it) || other == it) }

    infix fun Guard<A>.nand(other: A): Guard<A> = { !(invoke(it) && other == it) }


    infix fun Guard<A>.or(g: Guard<A>): Guard<A> = { invoke(it) || g(it) }

    infix fun Guard<A>.and(g: Guard<A>): Guard<A> = { invoke(it) && g(it) }

    infix fun Guard<A>.xor(g: Guard<A>): Guard<A> = { invoke(it) xor g(it) }

    infix fun Guard<A>.nor(g: Guard<A>): Guard<A> = { !(invoke(it) || g(it)) }

    infix fun Guard<A>.nand(g: Guard<A>): Guard<A> = { !(invoke(it) && g(it)) }


    infix fun Guard<A>.or(p: Predicate<A>): Guard<A> = { invoke(it) || p.test(it) }

    infix fun Guard<A>.and(p: Predicate<A>): Guard<A> = { invoke(it) && p.test(it) }

    infix fun Guard<A>.xor(p: Predicate<A>): Guard<A> = { invoke(it) xor p.test(it) }

    infix fun Guard<A>.nor(p: Predicate<A>): Guard<A> = { !(invoke(it) || p.test(it)) }

    infix fun Guard<A>.nand(p: Predicate<A>): Guard<A> = { !(invoke(it) && p.test(it)) }


    infix fun Guard<A>.or(condition: Boolean): Guard<A> = { invoke(it) || condition }

    infix fun Guard<A>.and(condition: Boolean): Guard<A> = { invoke(it) && condition }

    infix fun Guard<A>.xor(condition: Boolean): Guard<A> = { invoke(it) xor condition }

    infix fun Guard<A>.nor(condition: Boolean): Guard<A> = { !(invoke(it) || condition) }

    infix fun Guard<A>.nand(condition: Boolean): Guard<A> = { !(invoke(it) && condition) }


    operator fun Predicate<A>.not(): Guard<A> = { !test(it) }

    infix fun Predicate<A>.or(other: A): Guard<A> = { test(it) || other == it }

    infix fun Predicate<A>.and(other: A): Guard<A> = { test(it) && other == it }

    infix fun Predicate<A>.xor(other: A): Guard<A> = { test(it) xor other == it }

    infix fun Predicate<A>.nor(other: A): Guard<A> = { !(test(it) || other == it) }

    infix fun Predicate<A>.nand(other: A): Guard<A> = { !(test(it) && other == it) }


    infix fun Predicate<A>.or(g: Guard<A>): Guard<A> = { test(it) || g(it) }

    infix fun Predicate<A>.and(g: Guard<A>): Guard<A> = { test(it) && g(it) }

    infix fun Predicate<A>.xor(g: Guard<A>): Guard<A> = { test(it) xor g(it) }

    infix fun Predicate<A>.nor(g: Guard<A>): Guard<A> = { !(test(it) || g(it)) }

    infix fun Predicate<A>.nand(g: Guard<A>): Guard<A> = { !(test(it) && g(it)) }


    infix fun Predicate<A>.or(p: Predicate<A>): Guard<A> = { test(it) || p.test(it) }

    infix fun Predicate<A>.and(p: Predicate<A>): Guard<A> = { test(it) && p.test(it) }

    infix fun Predicate<A>.xor(p: Predicate<A>): Guard<A> = { test(it) xor p.test(it) }

    infix fun Predicate<A>.nor(p: Predicate<A>): Guard<A> = { !(test(it) || p.test(it)) }

    infix fun Predicate<A>.nand(p: Predicate<A>): Guard<A> = { !(test(it) && p.test(it)) }


    infix fun Predicate<A>.or(condition: Boolean): Guard<A> = { test(it) || condition }

    infix fun Predicate<A>.and(condition: Boolean): Guard<A> = { test(it) && condition }

    infix fun Predicate<A>.xor(condition: Boolean): Guard<A> = { test(it) xor condition }

    infix fun Predicate<A>.nor(condition: Boolean): Guard<A> = { !(test(it) || condition) }

    infix fun Predicate<A>.nand(condition: Boolean): Guard<A> = { !(test(it) && condition) }


    infix fun Boolean.or(other: A): Guard<A> = { this || other == it }

    infix fun Boolean.and(other: A): Guard<A> = { this && other == it }

    infix fun Boolean.xor(other: A): Guard<A> = { this xor other == it }

    infix fun Boolean.nor(other: A): Guard<A> = { !(this || other == it) }

    infix fun Boolean.nand(other: A): Guard<A> = { !(this && other == it) }


    infix fun Boolean.or(g: Guard<A>): Guard<A> = { this || g(it) }

    infix fun Boolean.and(g: Guard<A>): Guard<A> = { this && g(it) }

    infix fun Boolean.xor(g: Guard<A>): Guard<A> = { this xor g(it) }

    infix fun Boolean.nor(g: Guard<A>): Guard<A> = { !(this || g(it)) }

    infix fun Boolean.nand(g: Guard<A>): Guard<A> = { !(this && g(it)) }


    infix fun Boolean.or(p: Predicate<A>): Guard<A> = { this || p.test(it) }

    infix fun Boolean.and(p: Predicate<A>): Guard<A> = { this && p.test(it) }

    infix fun Boolean.xor(p: Predicate<A>): Guard<A> = { this xor p.test(it) }

    infix fun Boolean.nor(p: Predicate<A>): Guard<A> = { !(this || p.test(it)) }

    infix fun Boolean.nand(p: Predicate<A>): Guard<A> = { !(this && p.test(it)) }

//    @Suppress("EXTENSION_SHADOWED_BY_MEMBER")
//    infix fun Boolean.or(condition: Boolean): Guard<A> = { this || condition }
//
//    @Suppress("EXTENSION_SHADOWED_BY_MEMBER")
//    infix fun Boolean.and(condition: Boolean): Guard<A> = { this && condition }
//
//    @Suppress("EXTENSION_SHADOWED_BY_MEMBER")
//    infix fun Boolean.xor(condition: Boolean): Guard<A> = { this xor condition }

    infix fun Boolean.nor(condition: Boolean): Guard<A> = { !(this || condition) }

    infix fun Boolean.nand(condition: Boolean): Guard<A> = { !(this && condition) }

    private companion object {
        val alwaysTrue: Lazy<Guard<Any?>> = lazy { { true } }
    }
}

internal sealed class AbstractMatchScope<A, Y>(protected val argument: A) : MatchScope<A, Y>, Continuation<Unit> {
    override val context = EmptyCoroutineContext
}

internal class YieldScope<A, Y>(argument: A) : AbstractMatchScope<A, Y>(argument) {

    private lateinit var yield: Yield<A, Y>

    override fun resumeWith(result: Result<Unit>) {
        throw result.exceptionOrNull() ?: throw MatchFailedException(argument)
    }

    override suspend fun accept(guard: Guard<A>, yield: Yield<A, Y>) {
        if (guard(argument)) {
            suspendCoroutine<Unit> { this.yield = yield }
        }
    }

    internal fun yield(): Y = yield(argument)

}

internal class CanMatchScope<A, Y>(argument: A) : AbstractMatchScope<A, Y>(argument) {

    private var canMatch = false

    override fun resumeWith(result: Result<Unit>) = result.getOrThrow()

    override suspend fun accept(guard: Guard<A>, yield: Yield<A, Y>) {
        this.canMatch = canMatch || guard(argument)
    }

    internal fun canMatch(): Boolean = canMatch

}