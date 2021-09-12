package dev.iaiabot.furufuru.usecase.user

import dev.iaiabot.furufuru.repository.UserRepository
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object SaveUsernameUseCaseImplTest : Spek({
    lateinit var usecase: SaveUsernameUseCase

    val userRepository = initMockOnGroup<UserRepository>()

    describe("#save") {
        beforeGroup {
            coEvery { userRepository.getUserName() } returns ""
            coEvery { userRepository.saveUserName(any()) } answers {
                coEvery { userRepository.getUserName() } returns args.first() as String
            }
            usecase = SaveUsernameUseCaseImpl(userRepository)
        }

        xit("保存できてその値が返る") {
            runBlocking {
                usecase("iaia")
            }
        }
    }
})
