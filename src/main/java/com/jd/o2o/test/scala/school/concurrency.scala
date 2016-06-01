package com.jd.o2o.test.scala.school

import java.net.{Socket, ServerSocket}
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{FutureTask, Callable, Executors, ExecutorService}

/**
 * Created by Administrator on 2016/6/1.
 */


object ThreadTest extends App {

  // Runnable接口只有一个没有返回值的方法
  // Callable与之类似，除了它有一个返回值

  // 线程
  val hello = new Thread(new Runnable {
    override def run(): Unit = println("hello world")
  })

  hello.start()

  // 单线程代码
//  (new NetworkService(8088, 2)).run() // 打开该注释
//  (new Thread(new NetworkService(8088, 2))).start()

  // Futures：代表异步计算
  // 你可以把你的计算包装在Future中，当你需要计算结果的时候，你只需调用一个阻塞的 get() 方法就可以了
  // 一个 Executor 返回一个 Future
  // 一个 FutureTask 是一个Runnable实现，就是被设计为由 Executor 运行的
  val future = new FutureTask[String](new Callable[String]() {
    def call(): String = {
      // do something
      "hello future"
    }
  })

  val executor: ExecutorService = Executors.newFixedThreadPool(2)
  executor.execute(future)

  // 现在我需要结果，所以阻塞直到其完成
  val blockingResult = future.get
  println("blocking result = " + blockingResult)

}

// 单线程代码
class NetworkService(port: Int, poolSize: Int) extends Runnable {

  val serverSocket = new ServerSocket(port)
  // Executors
  val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

  def run(): Unit = {

    try {
      while(true) {
        val socket = serverSocket.accept()

//        (new Handler(socket)).run()
//        (new Thread(new Handler(socket))).start()
        pool.execute(new Handler(socket))
      }
    } finally {
      pool.shutdown()
    }
  }
}

class Handler(socket: Socket) extends Runnable {

  val threadName = Thread.currentThread.getName
  def message = (threadName + "\n").getBytes

  def run(): Unit = {
    socket.getOutputStream.write(message)
    socket.getOutputStream.close()

    println(threadName)
  }
}

// 同步：可以同步任何不为null的实例
class Person1(var name: String) {

  def set(changedName: String): Unit = {
    this.synchronized {
      name = changedName
    }
  }
}

// volatile和synchronized基本上是相同的，除了volatile允许空值
// synchronized 允许更细粒度的锁。 而 volatile 则对每次访问同步。
class Person2(@volatile var name: String) {

  def set(changedName: String): Unit = {
    name = changedName
  }
}

// 添加了一系列低级别的并发原语。 AtomicReference 类是其中之一
class Person3(val name: AtomicReference[String]) {

  def set(changedName: String): Unit = {
    name.set(changedName)
  }
}

// 这个成本是什么？
// AtomicReference 是这两种选择中最昂贵的，因为你必须去通过方法调度（method dispatch）来访问值。
// volatile 和 synchronized 是建立在Java的内置监视器基础上的。如果没有资源争用，监视器的成本很小。由于 synchronized 允许你进行更细粒度的控制权，从而会有更少的争夺，所以 synchronized 往往是最好的选择。
// 当你进入同步点，访问volatile引用，或去掉AtomicReferences引用时， Java会强制处理器刷新其缓存线从而提供了一致的数据视图。