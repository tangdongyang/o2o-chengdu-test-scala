package com.jd.o2o.test.scala.json4s

import org.json4s.JsonAST.JObject
import org.json4s.{DefaultFormats, Extraction, Formats}

/**
  * Created by tangdy2 on 2016/11/11.
  */
object SerializingFieldsOfaClass extends App {

  import org.json4s._
  import org.json4s.FieldSerializer._
  import org.json4s.jackson.Serialization
  import org.json4s.jackson.Serialization.{write, read}

  val dogSerializer = FieldSerializer[WildDog](
    renameTo("name", "animalName") orElse ignore("age"),
    renameFrom("animalName", "name")
  )

//  implicit val formats = DefaultFormats + FieldSerializer[WildDog]()
  implicit val formats = DefaultFormats + dogSerializer

  val ser = write(WildDog("dogName", "tdy002"))
  println(ser)

  println(read[WildDog](ser))

}

case class WildDog(name: String, owner: String) {
  var age: Int = 0
  val msg = owner + "'s " + name
}

//  Serializing classes defined in traits or classes
trait SharedModule {
  case class SharedObj(name: String, visible: Boolean = false)
}

object PingPongGame extends SharedModule

object SerializingClassesTest extends App {

  import org.json4s._
  import org.json4s.jackson._
  import org.json4s.jackson.Serialization
  import org.json4s.jackson.Serialization.{write, read}

  implicit val formats: Formats = DefaultFormats.withCompanions(classOf[PingPongGame.SharedObj] -> PingPongGame)

  val inst = PingPongGame.SharedObj("tdy002", visible = true)
  val extr = Extraction.decompose(inst)

  println(inst)
  println(extr)

//  extr must_== JObject("name" -> JString("tdy002"), "visible" -> JBool(true))
//  extr.extract[PingPongGame.SharedObj] must_== inst
}