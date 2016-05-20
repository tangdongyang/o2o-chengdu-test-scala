package com.jd.o2o.test.scala.school

/**
 * Created by Administrator on 2016/5/18.
 */

object BasicTypesTest extends App {

  // 参数化多态性：无法恢复其中成员的任何类型信息
  val list = 2 :: 1 :: "bar" :: "foo" :: Nil
  println(list)

  // 类型推断
  def id[T](x: T) = x

  val x1 = id(120)
  val x2 = id("Hey")

  println(x1 + ", " + x2)

  // 变性 Variance
  // 在混合OO和多态性时，一个核心问题是：如果T’是T一个子类，Container[T’]应该被看做是Container[T]的子类吗？
  // 协变covariant，C[T’]是 C[T] 的子类，[+T]
  // 逆变contravariant，C[T] 是 C[T’]的子类，[-T]
  // 不变invariant，C[T] 和 C[T’]无关，[T]

  // 协变
  val cv1: Covariant[AnyRef] = new Covariant[String]
//  val cv2: Covariant[String] = new Covariant[AnyRef]

  // 逆变
  val cv3: Contravariant[String] = new Contravariant[AnyRef]
//  val cv4: Contravariant[AnyRef] = new Contravariant[String]

  // 不变
//  val cv5: C[AnyRef] = new C[String]

  // 逆变似乎很奇怪。什么时候才会用到它呢？令人惊讶的是，函数特质的定义就使用了它！
  // trait Function1 [-T1, +R] extends AnyRef

  val getTweet: (Bird => String) = (a: Animal) => a.sound

  val hatch: (() => Bird) = () => new Chicken

  // 边界：Scala允许你通过 边界 来限制多态变量。这些边界表达了子类型关系。
  def biophony[T <: Animal](things: Seq[T]) = things map (_.sound)
  val list2 = biophony(Seq(new Chicken, new Bird))
  println(list2)

  // List 同样 定义了::[B >: T](x: B) 来返回一个List[B]。
  // 请注意B >: T，这指明了类型B为类型T的超类。

  // 量化：有时候，你并不关心是否能够命名一个类型变量
  def count1[A](l: List[A]) = l.size

  // 可以使用“通配符”取而代之
  def count2(l: List[_]) = l.size

  // 这相当于是下面代码的简写
  def count3(l: List[T forSome {type T}]) = l.size

  // 可以为通配符类型变量应用边界
  def hashCodes(l: Seq[_ <: AnyRef]) = l map (_.hashCode())
//  val hc1 = hashCodes(Seq(1, 2, 3)) // Int 不是 AnyRef 的子类
  val hc2 = hashCodes(Seq("c1", "c2", "c3")) // String 是 AnyRef 的子类
  println(hc2)

}

class Covariant[+A]
class Contravariant[-A]
class C[A]

class Animal { val sound = "animal"}
class Bird extends Animal { override val sound = "bird"}
class Chicken extends Bird { override val sound = "chicken"}