package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.data.local.UserDataSource
import dev.iaiabot.furufuru.furufuruTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object UserRepositoryImplTest : Spek({
    lateinit var repository: UserRepository
    lateinit var userDataSource: UserDataSource

    furufuruTestRule()

    beforeEachTest {
        repository = UserRepositoryImpl(userDataSource)
    }

    describe("#getUserName") {
        beforeEachGroup {
            userDataSource = mockk()
        }

        context("未保存のとき") {
            beforeGroup {
                every { userDataSource.getUserName() } returns ""
            }

            it("空が返る") {
                assertThat(repository.getUserName()).isEqualTo("")
            }
        }
        context("保存済みの時") {
            beforeGroup {
                every { userDataSource.getUserName() } returns "iaia"
            }

            it("保存済みのものが返る") {
                assertThat(repository.getUserName()).isEqualTo("iaia")
            }
        }
    }

    describe("#saveUserName") {
        beforeEachGroup {
            userDataSource = mockk()
            every { userDataSource.saveUserName(any()) } answers {}
        }

        it("データソースに保存している") {
            repository.saveUserName("iaia")
            verify { userDataSource.saveUserName("iaia") }
        }
    }
})
