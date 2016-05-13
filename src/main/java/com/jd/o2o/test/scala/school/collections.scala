package com.jd.o2o.test.scala.school

/**
 * Created by Administrator on 2016/5/11.
 */


object CollectionsTest extends App {

  // 列表 List
  val listNumbers = List(1, 2, 3, 4, 4)

  // 集 Set：没有重复
  val setNumbers = Set(1, 1, 2)

  // 元组 Tuple：元组是在不使用类的前提下，将元素组合起来形成简单的逻辑集合。
  // 与样本类不同，元组不能通过名称获取字段，而是使用位置下标来读取对象；而且这个下标基于1，而不是基于0。
  val hostPort = ("localhost", 8080)
  println(hostPort._1 + ":" + hostPort._2)

  hostPort match {
    case ("localhost", port) => "localhost:" + port
    case (host, port) => "host:" + port
  }

  // 在创建两个元素的元组时，可以使用特殊语法：->
  1 -> 2

  // 解除元组绑定
  val (host, port) = hostPort
  println(host + ":" + port)

  // 映射 Map
  Map(1 -> 2)
  Map("foo" -> "bar")
  Map(1 -> "foo", "bar" -> 2)
  Map(1 -> Map("foo" -> "bar"))

  // 选项 Option：是一个表示有可能包含值的容器
  val mapNumbers = Map("one" -> 1, 2 -> "two")
  val getTwo = mapNumbers.get(2)
  println(getTwo)

  val result1 = if(getTwo.isDefined) {
    getTwo.get
  } else {
    0
  }
  println(result1)

  val result2 = getTwo.getOrElse(0)
  println(result2)

  // 配合模式匹配
  val result3 = getTwo match {
    case Some(n) => n
    case None => 0
  }
  println(result3)

}


// apply and unapply
object Twice {
  def apply(x: Int): Int = {
    println("invoke apply")
    x * 2
  }

  def unapply(z: Int): Option[Int] = {
    println("invoke unapply")
    if(z % 2 == 0) Some(z / 2) else None
  }
}

object TwiceTest extends App {

  val x = Twice(21)
  println(x)

  x match {
    case Twice(n) => println(n)
  }
}