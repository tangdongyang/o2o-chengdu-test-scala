package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.Spec

/**
 * Created by Administrator on 2015/8/27.
 */
class SetSpec extends Spec {

  object `A Set` {
    object `when empty` {
      def `should have size 0` {
        assert(Set.empty.size == 0)
      }

      def `should produce NoSuchElementException when head is invoked` {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
