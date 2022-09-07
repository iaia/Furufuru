package dev.iaiabot.furufuru.feature

import dev.iaiabot.furufuru.usecase.MyExtension
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExtendWith(MyExtension::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("test1")
        assertEquals(4, 2 + 2)
    }

    @Test
    fun hoge() {
        println("test2")
        assertEquals(4, 2 + 2)
    }

    @Test
    fun fuga() {
        println("test3")
        assertEquals(4, 2 + 2)
    }
}
