import akka.actor._

case object EchoMessage
case object KillWorker

class WorkerActor extends Actor {
  def receive = {
    case EchoMessage =>
      println(s"${self.path.name} received EchoMessage")
    case KillWorker =>
      println(s"${self.path.name} received KillWorker and will stop")
      context.stop(self)
  }
}

class SupervisorActor(numWorkers: Int) extends Actor with ActorLogging {
  var workers: Seq[ActorRef] = Seq.empty

  override def preStart(): Unit = {
    for (i <- 1 to numWorkers) {
      val worker = context.actorOf(Props[WorkerActor], s"worker$i")
      workers = workers :+ worker
    }
    log.info(s"Initialized $numWorkers worker actors")
  }

  def receive = {
    case EchoMessage =>
      val randomWorker = workers(util.Random.nextInt(workers.size))
      log.info(s"${self.path.name} sent EchoMessage to ${randomWorker.path.name}")
      randomWorker ! EchoMessage
    case KillWorker =>
      val worker = sender()
      log.info(s"${worker.path.name} will be restarted")
      context.stop(worker)
      val newWorker = context.actorOf(Props[WorkerActor], worker.path.name)
      workers = workers.map(w => if (w == worker) newWorker else w)
  }
}

object SupervisorExample extends App {
  val system = ActorSystem("SupervisorExample")
  val supervisor = system.actorOf(Props(new SupervisorActor(numWorkers = 5)), "supervisor")

  supervisor ! EchoMessage
  Thread.sleep(1000)

  val workerToKill = supervisor ! KillWorker
  Thread.sleep(1000)

  supervisor ! EchoMessage
  Thread.sleep(1000)

  system.terminate()
}
