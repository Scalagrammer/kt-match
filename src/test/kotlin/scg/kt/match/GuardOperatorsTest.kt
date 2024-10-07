package scg.kt.match

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GuardOperatorsTest : Guards<String> {

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `not instance guard operator should works fine`(s: String) {
        assertFalse((!s)(s))
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `or instance guard operator should works fine`(s: String) {
        assertTrue((s or "")(s))
        assertTrue((s or "")(""))
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `xor instance guard operator should works fine`(s: String) {
        assertTrue((s xor "")(s))
        assertTrue((s xor "")(""))
        assertFalse((s xor s)(s))
        assertFalse((s xor s)(""))
    }

}