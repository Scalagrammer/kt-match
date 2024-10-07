package scg.kt.match

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class DefinedAtTest {

    private val partial : Partial<Any?, Any> = { "123" yield Unit }

    private val emptyPartial : Partial<Any?, Any> = {  }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `partial function should be defined at argument`(s: String) {
        assertTrue(partial.definedAt(s))
        assertDoesNotThrow {
            assertEquals(Unit, partial(s))
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["321"])
    fun `partial function should be undefined at argument`(s: String) {
        assertFalse(partial.definedAt(s))
        assertThrows<MatchFailedException> {
            partial(s)
        }.run {
            assertEquals(s, argument)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["321", "231", "132", ""])
    fun `empty partial function should be undefined at any argument`(s: String) {
        assertFalse(emptyPartial.definedAt(s))
        assertThrows<MatchFailedException> {
            emptyPartial(s)
        }.run {
            assertEquals(s, argument)
        }
    }

}