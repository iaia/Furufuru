package dev.iaiabot.furufuru

import io.mockk.unmockkAll
import org.spekframework.spek2.dsl.Root
import org.spekframework.spek2.lifecycle.ExecutionResult
import org.spekframework.spek2.lifecycle.LifecycleListener
import org.spekframework.spek2.lifecycle.TestScope

fun Root.furufuruTestRule() {
    registerListener(TestCallback())
}

private class TestCallback : LifecycleListener {

    override fun beforeExecuteTest(test: TestScope) {
        super.beforeExecuteTest(test)
    }

    override fun afterExecuteTest(test: TestScope, result: ExecutionResult) {
        unmockkAll()
    }
}
