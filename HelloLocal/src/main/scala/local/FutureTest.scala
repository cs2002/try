package local

import akka.actor.{Props, ActorSystem, Actor}
import akka.actor.Actor.Receive
import akka.util.Timeout

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask
import akka.pattern.pipe

import scala.util.{Failure, Success}
import scala.concurrent.blocking
// import scala.concurrent.ExecutionContext
// import java.util.concurrent.Executors
// import java.util.concurrent.ForkJoinPool

// object MyActor {
  // def fun = Future {
    // Thread.sleep(3000)
    // 1
    // //1/0
  // }
// }

// class MyActor extends Actor {
  // def receive = {
    // case 'hello =>
      // //Thread.sleep(15000);
      // sender ! 10
    // case value: String =>
      // println(self.hashCode());
      // val a = value;
      // println(a);
      // //MyActor.fun.foreach {item => println("Callback: " + a); println(item)}
      // MyActor.fun.pipeTo(self)
    // //case value => println(sender); println(value)
  // }

  // override def unhandled(msg: Any) = msg match {
    // case _ => println("wow")
  // }
// }

class MyActor2 extends Actor {
  // import context.dispatcher
  def receive = {
    case v =>
    // implicit val ec = context.system.dispatcher
    // implicit val ec = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
    // implicit val ec = ExecutionContext.fromExecutor(new ForkJoinPool(5))
    for (i <- (1 to 4)) {
      Future {
        println(i)
        blocking {
          Thread.sleep(10000)
        }
        println(2 * i)
      }
    }

    Thread.sleep(1000)
    for (j <- (1 to 10)) {
      Future {
        println("wow")
        Thread.sleep(8000)
      }
    }

    // Thread.sleep(1000)
    // for (j <- (1 to 5)) {
      // Future {
        // println("hello")
        // Thread.sleep(5000)
      // }
    // }

    Thread.sleep(10000)
  }
}

object FutureTest extends App {
/*  val f = Future {
    Thread.sleep(10000)
    "10"
  }

  val a = Await.result(f, 30 seconds)
  println(a)
  println("hi")*/

  val system = ActorSystem("actorsystem")
  val myActor = system.actorOf(Props[MyActor2], "myActor2")
  // myActor ! "hi"
  // myActor ! "he"
  myActor ! 10

/*  implicit val timeout = Timeout(10 seconds)
  val f = (myActor ? 'hello).mapTo[Int]

  val f2 = f.map {
    v => v * 2
  }
  f2.foreach {println}*/

  //val ret = Await.result(f, timeout.duration)
/*  f.onComplete {
    case Success(v) => myActor ! v
    case Failure(v) => myActor ! v
  }*/
/*  f.pipeTo(myActor)*/
/*  f.foreach {
    case v => println("ok"); println(v)
  }
  f.failed.foreach {
    case v => println("fail"); println(v)
  }*/

/*  val f1 = Future {
    "Hello" + "World"
  }
  val f2 = Future.successful(3)
  val f4 = Future.successful(4)*/
/*  val f3 = f1 flatMap { x =>
    f2 flatMap { y =>
      f4.map { z =>
        x.length * y * z
      }
    }
  }
  f3 foreach println*/
/*  val f3 = for {
    perF1 <- f1
    perF2 <- f2
    perF4 <- f4
  } yield (perF1.length * perF2 * perF4)
  f3 foreach println

  val f = Future(0)
  f.andThen {
    case r => println(r.get + "1")
  } andThen {
    case r => println(r.get + "2")
  }

  f2 fallbackTo f4

  val ff1 = Future { 1/0 }
  val ff2 = ff1 recover {
    case e: ArithmeticException => println("go"); 0
  }
  ff2.onComplete {
    case Success(v) => println(v)
    case Failure(v) => println(v)
  }

  Thread.sleep(10000)*/
}
