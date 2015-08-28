package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.WordSpec

/**
 * Created by Administrator on 2015/8/27.
 */
class SetWordSpec extends WordSpec {

  "A Set" when {
    "empty" should {
      "have size 0" in {
        assert(Set.empty.size == 0)
      }

      "produce NoSuceElementException when head is invoked" in {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
