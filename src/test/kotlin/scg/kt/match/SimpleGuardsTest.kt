package scg.kt.match

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.function.Predicate.isEqual
import kotlin.test.assertEquals

class SimpleGuardsTest {

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `accept should yield argument instance if guard returns true`(s: String) {
        match(s) {
            accept({ it == "123" }) { it }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `no match expected if guard returns always false`(s: String) {
        assertThrows<MatchFailedException> {
            match(s) {
                accept({ false }) {}
            }
        }.run {
            assertEquals(s, argument)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `instance guard should work fine with yieldBy`(s: String) {
        match(s) {
            "123" yieldBy { it }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `instance guard should work fine with yieldWith`(s: String) {
        match(s) {
            "123" yieldWith { this }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `instance guard should work fine with yield`(s: String) {
        match(s) {
            "123" yield s
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `peek guard should supply matchable argument instance and works fine with yieldBy`(s: String) {
        match(s) {
            peek { "123" == this } yieldBy { it }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `peek guard should supply matchable argument instance and works fine with yieldWith`(s: String) {
        match(s) {
            peek { "123" == this } yieldWith { this }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `peek guard should supply matchable argument instance and works fine with yield`(s: String) {
        match(s) {
            peek { "123" == this } yield s
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `isEqual predicate should work fine with yield`(s: String) {
        match(s) {
            isEqual<String>("123") yield s
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `isEqual predicate should work fine with yieldBy`(s: String) {
        match(s) {
            isEqual<String>("123") yieldBy { it }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `isEqual predicate should work fine with yieldWith`(s: String) {
        match(s) {
            isEqual<String>("123") yieldWith { this }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `true condition should work fine with yieldWith`(s: String) {
        match(s) {
            true yieldWith { this }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `true condition should work fine with yieldBy`(s: String) {
        match(s) {
            true yieldBy { it }
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `true condition should work fine with yield`(s: String) {
        match(s) {
            true yield s
        }.let {
            assertEquals(s, it)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `false condition should work fine with yieldWith`(s: String) {
        assertThrows<MatchFailedException> {
            match(s) {
                false yieldWith {}
            }
        }.run {
            assertEquals(s, argument)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `false condition should work fine with yieldBy`(s: String) {
        assertThrows<MatchFailedException> {
            match(s) {
                false yieldBy {}
            }
        }.run {
            assertEquals(s, argument)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123"])
    fun `false condition should work fine with yield`(s: String) {
        assertThrows<MatchFailedException> {
            match(s) {
                false yield Unit
            }
        }.run {
            assertEquals(s, argument)
        }
    }
}