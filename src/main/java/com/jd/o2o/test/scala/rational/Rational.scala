package com.jd.o2o.test.scala.rational

/**
 * Created by Administrator on 2015/10/13.
 */
class Rational(n: Int, d: Int) {

  def this(d: Int) = {
    this(0, d)
  }

  private def gcd(x: Int, y: Int): Int = {

    if(x == 0) y
    else if(x < 0) gcd(-x, y)
    else if(y < 0) -gcd(x, -y)
    else gcd(y % x, x)
  }

  private val g = gcd(n, d)

  val numer: Int = n / g
  val denom: Int = d / g

  def +(that: Rational) = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)

  def -(that: Rational) = new Rational(numer * that.denom - that.numer * denom, denom * that.denom)

  def *(that: Rational) = new Rational(numer * that.numer, denom * that.denom)

  def /(that: Rational) = new Rational(numer * that.denom, denom * that.numer)

  // 倒数
  def unary_~ = new Rational(denom, numer)

  override def toString() = "Rational: [" + numer + " / " + denom + "]"
}

object RunRational extends App {

  val r1 = new Rational(1, 3)
  val r2 = new Rational(2, 5)

  val r3 = r1 + r2
  val r4 = r1 - r2
  val r5 = r1 * r2
  val r6 = r1 / r2
  val r7 = ~ r1

  println(r3)
  println(r4)
  println(r5)
  println(r6)
  println(r7)
}
