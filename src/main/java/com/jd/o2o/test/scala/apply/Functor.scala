package com.jd.o2o.test.scala.apply

/**
 * Created by Administrator on 2015/11/26.
 */
class Functor {

  def apply(): String = "Doing something without arguments"
  def apply(i: Int): String = {
    if(0 == i) "Done."
    else "apply... " + apply(i - 1)
  }
}

object Functor extends App {

  val f = new Functor

  println(f())
  println(f(3))
}
