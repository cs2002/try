package local

import akka.actor.Actor.Receive
import akka.actor.{Props, Actor, ActorSystem}
import redis.clients.jedis.Jedis
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object RedisHelper {
  val jedis = new Jedis("10.192.224.45")

  def set(key: String, value: String): Unit = {
    jedis.set(key, value)
  }

  def incr(key: String): Unit = {
    jedis.incr(key)
  }

  def get(key: String): Int = {
    jedis.get(key).toInt
  }

  def getKey(key: String) = {
    val a = jedis.get(key)
    if (null == a) {
      None
    } else {
      Some(a)
    }
  }
}

class Actor1 extends Actor {
  override def receive: Receive = {
    case _ =>
      (1 to 20) foreach {
        item =>
          implicit val timeout = Timeout(100)
          println("Thread1:" + item + ":" + System.currentTimeMillis())
          val f = TryRedisID.actionActor ? "ask"
          f.onComplete{
            case Success(v) =>
              var a: Int = 0
              a = v.asInstanceOf[Int]

              println("Thread1:" + item + ":" + System.currentTimeMillis())
              println("Thread1: " + v)
          }
      }
  }
}

class Actor2 extends Actor {
  override def receive: Receive = {
    case _ =>
      (1 to 20) foreach {
        item =>
          implicit val timeout = Timeout(100)
          println("Thread2:" + item + ":" + System.currentTimeMillis())
          val f = TryRedisID.actionActor ? "ask"
          f.onComplete{
            case v =>
              println("Thread2:" + item + ":" + System.currentTimeMillis())
              println("Thread2: " + v)
          }
      }
  }
}

class ActionActor extends Actor {
  def receive = {
    case _ =>
      println(System.currentTimeMillis())
      RedisHelper.incr("resId")
      val a = RedisHelper.get("resId")
      println(a + "=" + System.currentTimeMillis())
      sender ! a
  }
}

object TryRedisID extends App {
  RedisHelper.set("resId", "0")
  val system = ActorSystem("mysys")
  val actor1 = system.actorOf(Props[Actor1], "actor1")
  val actor2 = system.actorOf(Props[Actor2], "actor2")
  val actionActor = system.actorOf(Props[ActionActor], "actionActor")
  actor1 ! "hi"
//  actor2 ! "hi"

//  val a = RedisHelper.getKey("abc")
//  println(a)
}
