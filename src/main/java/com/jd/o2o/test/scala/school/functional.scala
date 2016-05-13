package com.jd.o2o.test.scala.school

/**
 * Created by Administrator on 2016/5/12.
 */

object FunctionalTest extends App {

  val listNumbers = List(1, 2, 3, 4, 5)

  // map：map对列表中的每个元素应用一个函数，返回应用后的元素所组成的列表。
  val listByMap = listNumbers.map((i: Int) => i * 2)

  // foreach：foreach很像map，但没有返回值。foreach仅用于有副作用[side-effects]的函数。
  listByMap.foreach(println)

  // filter：filter移除任何对传入函数计算结果为false的元素。返回一个布尔值的函数通常被称为谓词函数[或判定函数]。
  val filterEvenNumbers = listNumbers.filter((i: Int) => i % 2 == 0)
  filterEvenNumbers.foreach(println)

  def isEven(i: Int): Boolean = i % 2 == 0

  val evenNumbers = listNumbers.filter(isEven)
  evenNumbers.foreach(println)

  // zip：zip将两个列表的内容聚合到一个对偶列表中。
  val listNumber2 = List("a", "b", "c")
  val zipNumbers1 = listNumbers.zip(listNumber2)
  zipNumbers1.foreach(println)

  val zipNumbers2 = listNumber2.zip(listNumbers)
  zipNumbers2.foreach(println(_))

  // partition：partition将使用给定的谓词函数分割列表。
  val partitionNumbers = listNumbers.partition(isEven)
  println(partitionNumbers._1)
  println(partitionNumbers._2)

  // find：find返回集合中第一个匹配谓词函数的元素。
  print("find number: ")
  val findNumber = listNumbers.find(_ > 3)
  println(findNumber.getOrElse(0))

  // drop：drop 将删除前i个元素
  print("drop numbers: ")
  val dropNumbers = listNumbers.drop(2)
  dropNumbers.foreach(println _)

  // dropWhile 将删除元素直到找到第一个匹配谓词函数的元素。
  // 例如，如果我们在numbers列表上使用dropWhile奇数的函数, 1将被丢弃（但3不会被丢弃，因为他被2“保护”了）。
  print("dropWhile numbers: ")
  val dropWhileNumbers = listNumbers.dropWhile(_ % 2 != 0)
  dropWhileNumbers.foreach(println)

  // foldLeft: 0为初始值（记住numbers是List[Int]类型），m作为一个累加器。
  val foldNumber = listNumbers.foldLeft(0)(_ + _)
  val foldNumber2 = listNumbers.foldLeft(0)((m: Int, n: Int) => m + n)
  println("foldNumber: " + foldNumber + ", " + foldNumber2)
  val foldNumbers = listNumbers.foldLeft(0) { (m: Int, n: Int) =>
    println("m: " + m + ", n = " + n)
    m + n
  }

  // foldRight：和foldLeft一样，只是运行过程相反。n是累加器，初始值为0
  val foldRightNumber = listNumbers.foldRight(0) { (m: Int, n: Int) =>
    println("m: " + m + ", n = " + n)
    m + n
  }

  // flatten：flatten将嵌套结构扁平化为一个层次的集合。
  val flattenNumbers = List(List(1, 2), List(2, 3), List(3, 4, 5)).flatten
  flattenNumbers.foreach(println)

  // flatMap：flatMap是一种常用的组合子，结合映射[mapping]和扁平化[flattening]。
  // flatMap需要一个处理嵌套列表的函数，然后将结果串连起来。
  println("flatMap numbers: ")
  val flatMapNumbers = List(List(1, 2), List(2, 3)).flatMap(x => x.map(_ * 2))
  flatMapNumbers.foreach(println)

  // 可以把它看做是“先映射后扁平化”的快捷操作：
  // 这个例子先调用map，然后可以马上调用flatten，这就是“组合子”的特征，也是这些函数的本质。
  val flatMapNumbers2 = List(List(1, 2), List(2, 3)).map((x: List[Int]) => x.map(_ * 2)).flatten
  flatMapNumbers2.foreach(println)

  // 扩展函数组合子。初始值为：List[Int]()，xs为列表累加器；
  // 对numbers中的每个元素x，都调用fn函数后，添加到xs列表中去
  def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
    numbers.foldRight(List[Int]()) { (x: Int, xs: List[Int]) =>
      fn(x) :: xs
    }
  }

  val ourMapNumbers = ourMap(listNumbers, (i: Int) => i * 3)
  println("ourMap numbers: ")
  ourMapNumbers.foreach(println)

  // map: Map可以被看作是一个二元组的列表，所以你写的函数要处理一个键和值的二元组。
  val mapNumbers = Map("steve" -> 100, "cdtdy" -> 150, "joe" -> 201)
  // 现在筛选出电话分机号码低于200的条目。因为参数是元组，所以你必须使用位置获取器来读取它们的键和值。呃！
  val filterMapNumbers1 = mapNumbers.filter((namePhone: (String, Int)) => namePhone._2 < 200)
  println("filter map numbers1: ")
  filterMapNumbers1.foreach(println)

  // 幸运的是，我们其实可以使用模式匹配更优雅地提取键和值。
  val filterMapNumbers2 = mapNumbers.filter({case (name, phone) => phone < 200})
  println("filter map numbers2: ")
  filterMapNumbers2.foreach(println)
}
