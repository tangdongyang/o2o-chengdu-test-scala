package com.jd.o2o.test.scala.school

/**
 * Created by Administrator on 2016/5/11.
 */

// apply方法
class FooT {}

object FooMaker {
  def apply() = {
    println("foo maker apply")
    new FooT()
  }
}

class Bar {
  def apply() = {
    println("bar apply")
    0
  }
}

// 单例对象可以和类具有相同的名称，此时该对象也被称为“伴生对象”。我们通常将伴生对象作为工厂使用。
// 下面是一个简单的例子，可以不需要使用’new’来创建一个实例了。
class Bar1(foo: String)

object Bar1 {
  def apply(foo: String) = new Bar1(foo)
}

// 调用时，带有()，表示自动执行apply方法
object TestApp extends App {

  val newFoo1 = FooMaker
  val newFoo2 = FooMaker()

  val bar = new Bar
  println(bar)
  println(bar())
}

// 单例对象：单例对象用于持有一个类的唯一实例。通常用于工厂模式。
object Timer {
  var count = 0

  def currentCount(): Long = {
    count += 1
    count
  }
}

object TimerTest extends App {

  println(Timer.currentCount())
}

// 函数即对象
// 函数是一些特质的集合。具体来说，具有一个参数的函数是Function1特质的一个实例。这个特征定义了apply()语法糖，让你调用一个对象时就像你在调用一个函数。

// Function1[Int, Int] 等于 Int => Int，表示：第一个Int为参数类型，第二个Int为返回值类型
object addOne extends Function1[Int, Int] {
  def apply(m: Int): Int = m + 1
}

class AddOne extends Function1[Int, Int] {
  def apply(m: Int): Int = m + 1
}

class AddTwo extends (Int => Int) {
  def apply(m: Int): Int = m + 2
}

// 模式匹配

case class Calculator(brand: String, model: String)

object MatchTest extends App {

  // 匹配值
  val times = 2
  val times2String = times match {
    case 1 => "one"
    case 2 => "two"
    case _ => "some other number"
  }
  println(times2String)

  // 使用守卫进行匹配
  val times2Str = times match {
    case i if i == 1 => "one"
    case i if i == 2 => "two"
    case _ => "some other number"
  }
  println(times2Str)

  // 匹配类型
  def bigger(o: Any): Any = {
    o match {
      case i: Int if i < 0 => i - 1
      case i: Int => i + 1
      case d: Double if d < 0.0 => d - 0.1
      case d: Double => d + 0.1
      case text: String => text + "s"
    }
  }

  // 匹配类成员
  def calcType(calc: Calculator) = calc match {
    case _ if calc.brand == "hp" && calc.model == "20B" => "financial"
    case _ if calc.brand == "hp" && calc.model == "48G" => "scientific"
    case _ if calc.brand == "hp" && calc.model == "30B" => "business"
    case _ => "unknown"
  }

  // 样本类就是被设计用在模式匹配中的
  def calcTypeCase(calc: Calculator) = calc match {
    case Calculator("hp", "20B") => "financial"
    case Calculator("hp", "48G") => "scientific"
    case Calculator("hp", "30B") => "business"
    case Calculator(ourBrand, ourModel) => "Calculator: %s %s is of unknown type".format(ourBrand, ourModel)
    case Calculator(_, _) => "unknown type"
    case _ => "unknown type"
    case c@Calculator(_, _) => "Calculator: %s of unknown type".format(c)
  }

  // 异常：当一个异常被捕获处理了，finally块将被调用；它不是表达式的一部分。
  try {
    1
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    println("finally")
  }

  // try也是面向表达式的
  val result: Int = try {
    1
  } catch {
    case e: Exception => {
      e.printStackTrace()
      0
    }
  } finally {
    println("finally")
  }
}
