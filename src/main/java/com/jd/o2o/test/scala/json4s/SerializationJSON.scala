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
  println(ser)

  println(read[Child](ser))
}

object SerializingPolymorphicLists extends App {

  import org.json4s._
  import org.json4s.jackson.Serialization
  import org.json4s.jackson.Serialization.{read, write}

  trait Animal
  case class Dog(name: String) extends Animal
  case class Fish(weight: Double) extends Animal

  case class Animals(animals: List[Animal])

//  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Dog], classOf[Fish])))
//  implicit val formats = Serialization.formats(FullTypeHints(List(classOf[Dog], classOf[Fish])))

  // Serialized JSON objects will get an extra field named 'jsonClass'
  // (the name can be changed by overriding 'typeHintFieldName' from Formats).
  implicit val formats = new Formats {
    override def dateFormat: DateFormat = DefaultFormats.lossless.dateFormat
    override val typeHints = ShortTypeHints(classOf[Dog] :: classOf[Fish] :: Nil)
    override val typeHintFieldName = "fieldName"
  }

  val ser = write(Animals(Dog("pluto") :: Fish(1.2) :: Nil))
  println(ser)

  println(read[Animals](ser))
}