package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth
import io.kotest.core.spec.style.DescribeSpec

class KotestTest1: DescribeSpec({
    describe("kotest test") {
        it("test") {
            val result = 100 + 200
            Truth.assertThat(result).isEqualTo(301)
        }
    }
})
