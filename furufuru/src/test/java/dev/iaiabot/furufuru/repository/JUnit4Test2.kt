package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class JUnit4Test2 {
    @Test
    fun calculate1() {
        val result = 10 + 20
        assertThat(result).isEqualTo(31)
    }
}
