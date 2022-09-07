#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME} #end

import ${SUPERCLASS_FQ}
import io.kotest.matchers.shouldBe

class ${NAME} : ${SUPERCLASS}({

  furufuruTestRule()

#if (${BEFORE_TEST} == "true")
  beforeTest {
  }

#end
#if (${AFTER_TEST} == "true")
  afterTest { (testCase, result) ->
  }

#end
#if (${TEST_METHODS}) ${TEST_METHODS} #end

})
