package com.jd.o2o.test.scala.school

/**
 * Created by Administrator on 2016/5/18.
 */

object AdvancedTypesTest extends App {

  // 视界（“类型类”）
  // 有时候，你并不需要指定一个类型是等/子/超于另一个类，你可以通过转换这个类来伪装这种关联关系。
  // 一个视界指定一个类型可以被“看作是”另一个类型。这对对象的只读操作是很有用的。

  // 隐 函数允许类型自动转换。更确切地说，在隐式函数可以帮助满足类型推断时，它们允许按需的函数应用。
  implicit def strToInt(x: String) = x.toInt

  val x: Int = "123"

  val y = math.max("123", 111)
  println(y)

  // 视界，就像类型边界，要求对给定的类型存在这样一个函数。您可以使用 <% 指定类型限制。
  val a1 = (new Container[String]).addInt("123")
  val a2 = (new Container[Int]).addInt(123)
//  val a3 = (new Container[Float]).addInt(123.2F)
  println(a1 + ", " + a2)

  // 其他类型限制：sum[B >: A](implicit num: Numeric[B]): B
  val sum1 = List(1, 2, 3).sum  // 不需要传入一个 num 参数；它是隐式设置的
//  val sum2 = List("1", "2", "3").sum // 字符串需要传sum参数

  // 方法可能会要求某种特定类型的“证据”。这时可以使用以下类型-关系运算符
  // A =:= B 	A 必须和 B相等
  // A <:< B 	A 必须是 B的子类
  // A <%< B 	A 必须可以被看做是 B - (没有 <%< 这个类型限制)

  val c1 = new Container2[Int](123).addInt
//  val c2 = new Container2[String]("123").addInt
  println(c1)

  val n1 = List(1, 2, 3, 4).min
  val n2 = List(1, 2, 3, 4).min(new Ordering[Int] { def compare(a: Int, b: Int) = b compare a })
  println(n1 + ", " + n2)

  // 更高级多态性类型 和 特设多态性
  // 可以被实现为几种类型的容器：Option、List等。
  // 你要定义可以使用这些容器里的值的接口，但不想确定值的类型
  val container = new Container3[List] { def put[A](x: A) = List(x); def get[A](m: List[A]) = m.head }
  container.put("Hey")
  container.put(123)

  // 结合隐式转换implicits使用容器，我们会得到“特设的”多态性：即对容器写泛型函数的能力。
  implicit val listContainer = new Container3[List] { def put[A](x: A) = List(x); def get[A](m: List[A]) = m.head }

  implicit val optionContainer = new Container3[Some] { def put[A](x: A) = Some(x); def get[A](m: Some[A]) = m.get }

  def tupleize[M[_]: Container3, A, B](fst: M[A], snd: M[B]) = {
    val c = implicitly[Container3[M]]
    c.put(c.get(fst), c.get(snd))
  }

  val list1 = tupleize(List(1), List(2))
  val some1 = tupleize(Some(1), Some(2))
  println(list1 + ", " + some1)

  // 结构类型
  // Scala 支持 结构类型 structural types — 类型需求由接口 构造 表示，而不是由具体的类型表示。
  // 这可能在很多场景都是相当不错的，但这个实现中使用了反射，所以要注意性能！
  def foo (x: { def get: Int }) = 123 + x.get
  println( foo(new { def get = 10 }) )

  // 抽象类型成员：在特质中，你可以让类型成员保持抽象。
  println( (new Foo { type A = Int; val x = 123 }).getX )
  println( (new Foo { type A = String; val x = "Hey" } ).getX )

  // 可以使用hash操作符来引用一个抽象类型的变量
  val foo1: Foo1[List]#t[Int] = List(1)

  // 类型擦除和清单
  // 正如我们所知道的，类型信息在编译的时候会因为 擦除 而丢失。
  // Scala的 清单（Manifests） 功能，使我们能够选择性地恢复类型信息。清单提供了一个隐含值，根据需要由编译器生成。
  (new MakeFoo[String]()).make
}

// 这是说 A 必须“可被视”为 Int 。
class Container[A <% Int] { def addInt(x: A) = 123 + x }

class Container2[A](value: A) { def addInt(implicit evidence: A =:= Int) = 123 + value }

trait Container3[M[_]] { def put[A](x: A): M[A]; def get[A](m: M[A]): A; }

trait Foo { type A; val x: A; def getX: A = x }

trait Foo1[M[_]] { type t[A] = M[A] }

class MakeFoo[A](implicit manifest: Manifest[A]) { def make: A = manifest.erasure.newInstance.asInstanceOf[A] }