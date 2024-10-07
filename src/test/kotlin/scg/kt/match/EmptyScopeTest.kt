package scg.kt.match

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class EmptyScopeTest {

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `empty partial function should throw MatchFailedException`(s: String) {
        assertThrows<MatchFailedException> {
            match<String, Unit>(s) {
                // empty definition
            }
        }.run {
            assertEquals(s, argument)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `partial function should rethrow body exceptions`(s: String) {
        val cause = RuntimeException()
        assertThrows<RuntimeException> {
            match<String, Unit>(s) { throw cause }
        }.let {
            assertEquals(cause, it)
        }
    }

}