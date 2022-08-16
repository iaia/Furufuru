package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test

class JUnit5Test1 {
    @Test
    fun calculate1() {
        val result = 1 + 2
        Truth.assertThat(result).isEqualTo(4)
    }
}
