package Lab1.week3

import akka.actor.{Actor, ActorSystem, Props, Terminated}

//first task
class PrintActor extends Actor {

  override def receive: Receive = {
    case message: String => println("Message: " + message)
  }
}
//first task

//second task
class ModifyActor extends Actor {
  override def receive: Receive = {
    case i: Int => println("Recieved: " + (i % 10) )
    case s: String => println("Recieved: " + s.toLowerCase() )
    case a: Any => println("Recieved :I dont know how to handle this!")
  }
}
//second task

//third task

//this actor can only stop
class SecondActor extends Actor {
  override def receive: Receive = {
    case "stop" => context.stop(self)
  }
}

class FirstActor extends Actor {
  val secondActor = context.actorOf(Props[SecondActor], "secondActor")
  //just watched what second actor does
  context.watch(secondActor)

  override def receive : Receive = {
    case Terminated(actor) if actor == secondActor => println("Second actor has stopped")
    case "stop" => secondActor ! "stop"
  }
}
//third task

//  fourth task

class AverageActor(var totalSum: Double, var count: Int) extends Actor {
  override def receive : Receive = {
    case num : Double =>
      totalSum += num
      count += 1
      val average = totalSum / count
      println("Current average is " + average)
  }
}
//  fourth task

object Main {
  def main(args: Array[String]) : Unit = {

//    first task
    val system = ActorSystem("SimpleSystem")
    val printActor = system.actorOf(Props[PrintActor], "PrintActor")
    printActor ! "Hello My friends"
//    second task
    val Pid = system.actorOf(Props[ModifyActor], "ModifyActor")
    Pid ! 10
    Pid ! "Hello World"
    Pid ! (10, "Hello")

//  third task
    val firstActor = system.actorOf(Props[FirstActor], "firstActor")
    firstActor ! "stop"

//    fourth task

    val average = system.actorOf(Props(new AverageActor(0.0, 0)), "averageActor")
    average ! 10.0
    average ! 5.0
    average ! 10.0
  }
}
