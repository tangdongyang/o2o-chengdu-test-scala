package com.jd.o2o.test.scala.javaUseScala

/**
 * Created by Administrator on 2015/10/16.
 */
trait Student {

  def getFirstName: String
  def getLastName: String
  def setFirstName(fn: String): Unit
  def setLastName(ln: String): Unit

  def teach(subject: String)
}
