package com.jd.o2o.test.scala.javaUseScala

/**
 * Created by Administrator on 2015/10/16.
 */
object StudentFactory {

  class StudentImpl(var first: String, var last: String, var subject: String) extends Student {

    override def getFirstName: String = first

    override def setLastName(ln: String): Unit = last = ln

    override def teach(subject: String): Unit = println("I know: " + subject)

    override def setFirstName(fn: String): Unit = first = fn

    override def getLastName: String = last
  }

  def getStudent(firstName: String, lastName: String, subject: String): Student = {

    new StudentImpl(firstName, lastName, subject)
  }
}
