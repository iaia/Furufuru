package dev.iaiabot.furufuru.data.repository

import android.util.LruCache
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object ScreenshotRepositoryImplTest : Spek({
    lateinit var repository: ScreenshotRepository
    lateinit var cache: LruCache<String, String>

    beforeGroup {
        cache = mockk()
        repository = ScreenshotRepositoryImpl(cache)
    }

    describe("#get") {
        context("未保存のとき") {
            beforeGroup {
                every { cache.get(any()) } returns null
                every { cache.remove(any()) } returns null
            }

            it("nullが返る") {
                Truth.assertThat(repository.get()).isEqualTo(null)
            }

            it("取得したあと削除している") {
                repository.get(true)
                verifyOrder {
                    cache.get(any())
                    cache.remove(any())
                }
            }
        }
        context("保存済みの時") {
            beforeGroup {
                every { cache.get(any()) } returns "abcd"
            }

            it("保存済みのものが返る") {
                Truth.assertThat(repository.get()).isEqualTo("abcd")
            }
        }
    }
})
