package dev.iaiabot.furufuru.testtool

import io.mockk.clearMocks
import io.mockk.mockk
import org.spekframework.spek2.dsl.LifecycleAware

inline fun <reified T : Any> LifecycleAware.initMockOnGroup(): T {
    var double: T = mockk()

    beforeGroup {
        double = mockk(relaxed = true)
    }

    afterGroup {
        clearMocks(double)
    }

    return double
}
