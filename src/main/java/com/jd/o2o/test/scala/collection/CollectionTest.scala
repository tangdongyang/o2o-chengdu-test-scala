package com.jd.o2o.test.scala.collection

import java.text.SimpleDateFormat

import org.scalatest.FlatSpec

/**
 * Created by Administrator on 2015/10/19.
 */
class CollectionTest extends FlatSpec {

  // Option[T]
  val footballTeamsAFCEast = Map(
    "New England" -> "Patriots",
    "New York" -> "Jets",
    "Buffalo" -> "Bills",
    "Miami" -> "Dolphins",
    "Los Angeles" -> null
  )

  assert(footballTeamsAFCEast.get("Miami") == Some("Dolphins"))
  assert(footballTeamsAFCEast.get("Miami").get == "Dolphins")
  assert(footballTeamsAFCEast.get("Los Angeles") == Some(null))
  assert(footballTeamsAFCEast.get("tdy002") == None)

  def show(value: Option[String]): String = {
    value match {
      case Some(x) => x
      case None => "No team found"
    }
  }

  assert(show(footballTeamsAFCEast.get("Miami")) == "Dolphins")

  // tuple
  val sdf = new SimpleDateFormat("yyyy-MM-dd")
  val date = sdf.parse("2015-10-19")

  val tuple = ("Ted", "Scala", date)
  assert(tuple._1 == "Ted")
  assert(tuple._2 == "Scala")
  assert(tuple._3 == date)

  // array
  val array = Array(1, 2, 3)
  array.foreach(println)

  def increment(n: Int): Int = {
    n + 1
  }

  array.map(increment).foreach(println)

  def even(n: Int): Boolean = {
    n % 2 == 0
  }

  array.filter(even).foreach(println)

  // list
  val myFirstList = List("Java", "Scala", "Python")

  assert(myFirstList.isEmpty == false)
  assert(myFirstList.head == "Java")
  assert(myFirstList.tail == List("Scala", "Python"))
  assert(myFirstList.last == "Python")

  def count(VIPs: List[String]): Int = {
    if(VIPs.isEmpty) {
      0
    } else {
      count(VIPs.tail) + 1
    }
  }

  assert(count(myFirstList) == myFirstList.length)

  val myVIPList = "Ted" :: "Amanda" :: "Luke" :: "Don" :: "Martin" :: Nil

//  myVIPList.foreach(println)
  assert(count(myVIPList) == myVIPList.length)

  var foundAmanda = false
  def count_2(VIPs: List[String]): Int = {
    VIPs match {
      case "Amanda" :: t => println("Hey, Amanda"); foundAmanda = true; count_2(t) + 1
      case h :: t => count_2(t) + 1
      case Nil => 0
    }
  }

  assert(count_2(myVIPList) == myVIPList.length)
  assert(foundAmanda == true)

}
