package dev.iaiabot.furufuru.usecase

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.data.repository.UserRepository
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object UsernameUseCaseImplTest : Spek({
    lateinit var usecase: UsernameUseCase

    val userRepository = initMockOnGroup<UserRepository>()

    describe("#load") {
        context("設定済みの場合") {
            beforeGroup {
                coEvery { userRepository.getUserName() } returns ""
                usecase = UsernameUseCaseImpl(userRepository)
            }

            it("設定済みの名前が返る") {
                val result = runBlocking { usecase.load() }

                assertThat(result).isEqualTo("")
            }
        }

        context("未設定の場合") {
            beforeGroup {
                coEvery { userRepository.getUserName() } returns "iaia"
            }

            it("設定済みの名前が返る") {
                val result = runBlocking { usecase.load() }

                assertThat(result).isEqualTo("iaia")
            }
        }
    }

    describe("#save") {
        beforeGroup {
            coEvery { userRepository.getUserName() } returns ""
            coEvery { userRepository.saveUserName(any()) } answers {
                coEvery { userRepository.getUserName() } returns args.first() as String
            }
            usecase = UsernameUseCaseImpl(userRepository)
        }

        it("保存できてその値が返る") {
            val result = runBlocking {
                assertThat(usecase.load()).isEmpty()
                usecase.save("iaia")
                usecase.load()
            }

            assertThat(result).isEqualTo("iaia")
        }
    }
})
