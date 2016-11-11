package com.jd.o2o.test.scala.json4s

/**
  * Created by tangdy2 on 2016/11/11.
  *
  * id应该为Int类型
  * user只有一个的时候，应该为数组，而不是对象
  */
object XmlSupport extends App {

  import org.json4s._
  import org.json4s.jackson.JsonMethods._
  import org.json4s.Xml.{toJson, toXml}

  val xml =
    <users>
      <user>
        <id>1</id>
        <name>tdy001</name>
      </user>
      <user>
        <id>2</id>
        <name>tdy002</name>
      </user>
    </users>

  val json = toJson(xml)

  val jsonTransformField = json transformField {
    case ("id", JString(s)) => ("id", JInt(s.toInt))
    case ("user", x: JObject) => ("user", JArray(x :: Nil))
  }

  println(pretty(render(json)))
  println(pretty(render(jsonTransformField)))

  println(json \\ "name")


  val xml_2 =
    <users>
      <user>
        <id>1</id>
        <name>tdy001</name>
      </user>
    </users>

  val json_2 = toJson(xml_2)
  println(compact(render(json_2)))

  val jsonTransformField_2 = json_2 transformField {
    case ("id", JString(s)) => ("id", JInt(s.toInt))
    case ("user", x: JObject) => ("user", JArray(x :: Nil))
  }

  println(compact(render(jsonTransformField_2)))

  println(toXml(jsonTransformField_2))
  println(toXml(jsonTransformField))
}
