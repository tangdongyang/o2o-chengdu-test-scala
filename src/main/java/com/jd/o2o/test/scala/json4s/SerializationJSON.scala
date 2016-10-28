package com.jd.o2o.test.scala.json4s

/**
  * Created by tangdy2 on 2016/10/27.
  */
object SerializationJSON extends App {

  import org.json4s._
  import org.json4s.jackson.Serialization
  import org.json4s.jackson.Serialization.{read, write}

  implicit val formats = Serialization.formats(NoTypeHints)

  case class Child(name: String, age: Int, birthdate: Option[java.util.Date])

  val ser = write(Child("tdy", 26, None))

  println(read[Child](ser))
}
