package dev.iaiabot.furufuru.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object UserRepositoryImplTest : Spek({
    lateinit var repository: UserRepository
    lateinit var context: Context
    lateinit var prefs: SharedPreferences

    beforeGroup {
        context = mockk()
        prefs = mockk()
        every { context.getSharedPreferences("furufuru", Context.MODE_PRIVATE) } returns prefs
        repository = UserRepositoryImpl()
    }

    describe("#getUserName") {
        context("未保存のとき") {
            beforeGroup {
                every { prefs.getString(any(), any()) } returns null
            }

            it("空が返る") {
                assertThat(repository.getUserName(context)).isEqualTo("")
            }
        }
        context("保存済みの時") {
            beforeGroup {
                every { prefs.getString(any(), any()) } returns "iaia"
            }

            it("保存済みのものが返る") {
                assertThat(repository.getUserName(context)).isEqualTo("iaia")
            }
        }
    }

    describe("#saveUserName") {
        val editor: SharedPreferences.Editor = mockk()

        beforeGroup {
            every { prefs.edit() } returns editor
            every { editor.putString(any(), any()) } returns editor
            every { editor.apply() } answers {}
        }

        it("保存する") {
            repository.saveUserName(context, "iaia")
            verify { editor.putString("username", "iaia") }
            verify { editor.apply() }
        }
    }
})
