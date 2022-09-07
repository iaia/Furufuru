package dev.iaiabot.furufuru

import io.kotest.core.TestConfiguration
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.mockk.unmockkAll
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.spekframework.spek2.dsl.Root
import org.spekframework.spek2.lifecycle.ExecutionResult
import org.spekframework.spek2.lifecycle.LifecycleListener
import org.spekframework.spek2.lifecycle.TestScope

class FurufuruTestRuleExtension : Extension, BeforeAllCallback, AfterAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
    }

    override fun afterAll(context: ExtensionContext?) {
        unmockkAll()
    }
}

fun Root.furufuruTestRule() {
    registerListener(SpekTestCallback())
}

private class SpekTestCallback : LifecycleListener {

    override fun beforeExecuteTest(test: TestScope) {
        super.beforeExecuteTest(test)
    }

    override fun afterExecuteTest(test: TestScope, result: ExecutionResult) {
        unmockkAll()
    }
}

fun TestConfiguration.furufuruTestRule() {
    extension(KotestListener())
}

private class KotestListener : BeforeSpecListener, AfterSpecListener {

    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
    }

    override suspend fun afterSpec(spec: Spec) {
        unmockkAll()
    }
}
