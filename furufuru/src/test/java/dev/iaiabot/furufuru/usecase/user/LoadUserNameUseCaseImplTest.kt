package dev.iaiabot.furufuru.usecase.user

import com.google.common.truth.Truth
import dev.iaiabot.furufuru.furufuruTestRule
import dev.iaiabot.furufuru.repository.UserRepository
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LoadUserNameUseCaseImplTest : Spek({
    lateinit var usecase: LoadUserNameUseCase

    furufuruTestRule()

    val userRepository = initMockOnGroup<UserRepository>()

    describe("#invoke") {
        context("設定済みの場合") {
            beforeGroup {
                coEvery { userRepository.getUserName() } returns ""
                usecase = LoadUserNameUseCaseImpl(userRepository)
            }

            it("設定済みの名前が返る") {
                val result = runBlocking { usecase() }

                Truth.assertThat(result).isEqualTo("")
            }
        }

        context("未設定の場合") {
            beforeGroup {
                coEvery { userRepository.getUserName() } returns "iaia"
            }

            it("設定済みの名前が返る") {
                val result = runBlocking { usecase() }

                Truth.assertThat(result).isEqualTo("iaia")
            }
        }
    }
})
