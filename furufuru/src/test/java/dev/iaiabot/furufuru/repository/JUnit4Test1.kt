package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class JUnit4Test1 {
    @Test
    fun calculate1() {
        val result = 1 + 2
        assertThat(result).isEqualTo(4)
    }
}
