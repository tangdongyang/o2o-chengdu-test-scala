package com.jd.o2o.test.scala.school

/**
 * Created by Administrator on 2016/6/1.
 */

object MoreCollections extends App {

  // 表 List：标准的链表
  List(1, 2, 3)
  // 你可以用函数式语言的方式连接它们
  1 :: 2 :: 3 :: Nil

  // 集 Set：集没有重复
  Set(1, 1, 2)

  // 序列 Seq：序列有一个给定的顺序
  Seq(1, 1, 2)

  // 映射 Map：映射是键值容器
  Map("a" -> 1, "b" -> 2)
  Map.empty ++ List(("a", 1), ("b", 2), ("c", 3))

  // 什么是->？这不是特殊的语法，这是一个返回元组的方法
  "a" -> 1 // ("a", 1)
  "a".->(1) // 语法糖

}
