package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.data.entity.User
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object UserRepositoryImplTest : Spek({
    lateinit var repository: UserRepository
    lateinit var user: User

    beforeGroup {
        user = mockk()
        repository = UserRepositoryImpl(user)
    }

    describe("#getUserName") {
        context("未保存のとき") {
            beforeGroup {
                every { user.getUserName() } returns ""
            }

            it("空が返る") {
                assertThat(repository.getUserName()).isEqualTo("")
            }
        }
        context("保存済みの時") {
            beforeGroup {
                every { user.getUserName() } returns "iaia"
            }

            it("保存済みのものが返る") {
                assertThat(repository.getUserName()).isEqualTo("iaia")
            }
        }
    }

    describe("#saveUserName") {
        beforeGroup {
            every { user.saveUserName(any()) } answers {
                every { user.getUserName() } returns firstArg()
            }
        }

        it("保存する") {
            assertThat(user.getUserName()).isEqualTo("")
            repository.saveUserName("iaia")
            assertThat(user.getUserName()).isEqualTo("iaia")
        }
    }
})
