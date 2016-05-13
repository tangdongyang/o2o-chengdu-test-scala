package com.jd.o2o.test.scala.school

/**
 * Created by Administrator on 2016/5/13.
 */

object ComposeTest extends App {

  def f(s: String) = "f(" + s + ")"

  def g(s: String) = "g(" + s + ")"

  // compose: compose 组合其他函数形成一个新的函数 f(g(x))
  val fComposeG = f _ compose g _

  // 先执行函数g，再执行函数f
  val fComposeGValue = fComposeG("hey")
  println(fComposeGValue)

  // andThen: andThen 和 compose很像，但是调用顺序是先调用第一个函数，然后调用第二个，即g(f(x))
  val fAndThenG = f _ andThen g _

  // 先执行函数f，再执行函数g
  val fAndThenGValue = fAndThenG("hi")
  println(fAndThenGValue)

  // case 语句
  // 那么究竟什么是case语句？ 这是一个名为PartialFunction的函数的子类。
  // 多个case语句的集合是什么？ 他们是共同组合在一起的多个PartialFunction。

  // 理解PartialFunction(偏函数)
  // 对给定的输入参数类型，函数可接受该类型的任何值。换句话说，一个(Int) => String 的函数可以接收任意Int值，并返回一个字符串。
  // 对给定的输入参数类型，偏函数只能接受该类型的某些特定的值。一个定义为(Int) => String 的偏函数可能不能接受所有Int值为输入。
  // isDefinedAt 是PartialFunction的一个方法，用来确定PartialFunction是否能接受一个给定的参数。
  // 注意 偏函数PartialFunction 和我们前面提到的部分应用函数是无关的。

  val one: PartialFunction[Int, String] = {case 1 => "one"}
  println(one.isDefinedAt(1))
  println(one.isDefinedAt(2))
  println(one(1))


  val two: PartialFunction[Int, String] = {case 2 => "two"}
  val three: PartialFunction[Int, String] = {case 3 => "three"}
  val wildcard: PartialFunction[Int, String] = {case _ => "something else"}

  // PartialFunctions可以使用orElse组成新的函数，得到的PartialFunction反映了是否对给定参数进行了定义。
  val partial = one orElse two orElse three orElse wildcard
  println(partial(5))
  println(partial(3))
  println(partial(2))
  println(partial(1))
  println(partial(0))

  val pf: PartialFunction[Int, String] = {
    case i if i % 2 == 0 => "even"
  }

  val tf: (Int => String) = pf orElse {case _ => "odd"}
  println(tf(1) + ", " + tf(2))

  // case 之谜
  val extensions = List(PhoneExt("steve", 100), PhoneExt("cdtdy", 150), PhoneExt("joe", 201))
  val filterNumber = extensions.filter {
    case PhoneExt(name, ext) => ext < 200
  }
  println(filterNumber)
}

case class PhoneExt(name: String, ext: Int)
