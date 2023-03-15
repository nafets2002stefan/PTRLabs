# FAF.PTR16.1 -- Project 0
> **Performed by:** Berestean Stefan, group FAF-201
> **Verified by:** asist. univ. Alexandru Osadcenco

## P0W1

**Task 1** -- (Minimal) Write a script that would print the message “Hello PTR” on the screen.
Execute it.

```scala
object HelloPTR {
  def main(args: Array[String]): Unit = {
    print ("Hello PTR")
  }
}
```

That's the introduction task. We have the main function that doesn't return nothing, 
that's why we put "Unit" something like return None and print "Hello PTR".


**Task 2** -- (Bonus) Create a unit test for your project. Execute it.


```scala
  class DisplayHelloPtrTest extends AnyFunSuite {
  test("HelloPTR.main") {
    val result = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(result)) {
      HelloPTR.main(Array.empty[String])
    }
    assert(result.toString == "Hello PTR")
  }
}
```
First we have function "test()" where we indicate the name of file which
 we want to check. In this function we expect nothing to return.
But we assert that result which will be printed will be "Hello PTR". Else it will
 raise an error.

## P0W2

**Task 1** -- (Minimal) Write a function to calculate the sum of unique elements in a list.
 uniqueSum ([1 , 2 , 4 , 8 , 4 , 2]) -> 15


```scala
  def uniqueSum(list: Array[Int]): Int = {
 var uniqueValues: Set[Int] = Set()
 for(i <- 0 until list.length)
  uniqueValues += list(i)
 uniqueValues.sum
}
```

We need to calculate the unique sum of a list. As we know from data structures, 
a set is similar to list but doesn't accept duplicates. So we just insert all elements into set and
traverse to calculate sum.

**Task 2** -- (Main) Write a function that eliminates consecutive duplicates in a list.
removeConsecutiveDuplicates ([1 , 2 , 2 , 2 , 4 , 8 , 4]) -> [1 , 2 , 4 , 8 , 4]

```scala
def removeConsecutiveDuplicates(list : Array[Int]) : Array[Int] = {
 var finalList = Array.empty[Int]
 var tmp = -1

 for(i <- 0 to list.length - 1)
  if(tmp != list(i)) {
   tmp = list(i)
   finalList = finalList :+ list(i)
  }

  else {}

 finalList
}
```

To remove consecutive duplicates we should have a temporary variable which will store current
value of element from list,also another empty list. Traverse initial list, if the temporary variable is not
 the same as current from list, we add this value to new list.

**Task 3** -- (Bonus) Write a function to calculate the prime factorization of an integer.
factorize (13) -> 13

```scala
  def factorize(number : Int) : List[Int] = {
    var finalList = List[Int]()
    var n = number

    for( i <- 2 to n)
      while(n % i == 0) {
        finalList = i :: finalList
        n = n / i
      }
    finalList
  }
```

For this task we'll need an empty list and a variable "n" which will
 be initially equal to number that we inserted. With for loop we iterate 
i till "n % i" will divide without remainder. Add i to list  and divide n to i .
That's a mathematical formula which I found on internet to factorize an integer.

## P0W3

**Task 1** -- (Minimal) Create an actor that prints on the screen any message it receives.

```scala
class PrintActor extends Actor {

  override def receive: Receive = {
    case message: String => println("Message: " + message)
  }
}
```

Here we have a method recieve that's mandatory to implement if class is extended from Actor.
Now "message" that we recieve print with "println" function.

**Task 2** -- (Minimal) Create an actor that returns any message it receives, while modifying it. Infer
the modification from the following example:

1 > Pid ! 10. % Integers
2 Received : 11
3 > Pid ! " Hello ". % Strings
4 Received : hello
5 > Pid ! {10 , " Hello "}. % Anything else
6 Received : I don ’ t know how to HANDLE this !

```scala
class ModifyActor extends Actor {
  override def receive: Receive = {
    case i: Int => println("Recieved: " + (i % 10) )
    case s: String => println("Recieved: " + s.toLowerCase() )
    case a: Any => println("Recieved :I dont know how to handle this!")
  }
} 
```

In "recieve" function we have 3 cases. If input is an integer,print number % 10.
If is a string, print message, but to lowercase. If it is anything else, print that is an unknown error.

## P0W4

**Task 1** -- (Minimal) Create a supervised pool of identical worker actors. The number of actors
is static, given at initialization. Workers should be individually addressable. Worker actors
should echo any message they receive. If an actor dies (by receiving a “kill” message), it should
be restarted by the supervisor. Logging is welcome.

```scala
class WorkerActor extends Actor {
  def receive = {
    case EchoMessage =>
      println(s"${self.path.name} received EchoMessage")
    case KillWorker =>
      println(s"${self.path.name} received KillWorker and will stop")
      context.stop(self)
  }
}
```
Here we have a class which extends Actor. We have 2 cases echo and kill. First case
just prints the message. Second case we use context.stop to stop the actor.


```scala
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
```

In the first function we start each actor and put in a section.
Another function recieve has 2 cases. First case "EchoMessage" takes a random worker using "Random.nextInt"
and prints the message. In second case "KillWorker", first of all we kill worker.After that start again and 
print this information.

## P0W5

**Task 1** -- (Minimal) Write an application that would visit this link. Print out the HTTP response
status code, response headers and response body.

```scala
    implicit val system: ActorSystem = ActorSystem("website-reader")
    val url = "https://quotes.toscrape.com/"
    val request = HttpRequest(uri = url)
    val responseFuture= Http().singleRequest(request)

    responseFuture
      .flatMap { response =>
        val status = response.status
        val headers = response.headers
        response.entity.dataBytes.runFold(ByteString(""))(_ ++ _).map { body =>
          println(s"Response status: $status")
          println("Response headers:")
          headers.foreach(header => println(s"${header.name}: ${header.value}"))
```

Here we use akka for working with HTTP. With Http().singleRequest() we make a request
to resource which was indicated earlier. After that we use flatMap but not simple 
map because flatmap traverse each element individually but map not. We should extract status, 
headers, body. For that we extract response.status or response.headers or response.body in dependence of
 our needs.

**Task 2** - (Minimal) Continue your previous application. Extract all quotes from the HTTP
response body. Collect the author of the quote, the quote text and tags. Save the data
into a list of maps, each map representing a single quote.

```scala
          val document = Jsoup.parse(body.utf8String)

          val quotes = document.select(".quote").map { quoteElement =>
            val author = quoteElement.select(".author").text()
            val text = quoteElement.select(".text").text()
            val tags = quoteElement.select(".tags .tag").map(_.text()).toList
            Map("author" -> author, "text" -> text, "tags" -> tags)
          }.toList

          println(s"Extracted ${quotes.size} quotes:")
          quotes.foreach(quote => println(s"Author: ${quote("author")}, Text: ${quote("text")}, Tags: ${quote("tags")}"))

```

To extract body into a list of maps, we need to use Jsoup. After that we are selecting
 the parent class and map each child element to coresponding key (ex. "author", "text", "tags").
 To print each quote beautifully, we can use foreach to traverse every element from list and  print by accessing 
element from map.

**Task 3** - (Minimal) Continue your previous application. Persist the list of quotes into a file.
Encode the data into JSON format. Name the file quotes.json.

```scala
          val mapper = new ObjectMapper().registerModule(DefaultScalaModule)
          val json = mapper.writeValueAsString(quotes)
          java.nio.file.Files.write(java.nio.file.Paths.get("quotes.json"), json.getBytes())
          println("Quotes saved to file 'quotes.json'")
```

For that, we declare a mapper. With this mapper convert our list of maps into json file
 using "mapper.writeValueAsString()". When we have already converted json file, simply 
use a library that works with files(in my case java.nio.file). In write function, first parameter 
is name of file and directory (I selected current directory to save and quotes.json naming). Second
argument is our converted json value.

## Conclusion

This project was quite interesting especially I have never programmed in Scala
 and this was a new experience for me. First weeks were like an introduction into language, not very complicated
 and quite similar like in another languages. But when everything started about Actor Model, 
was different and in some moments challenging. It is similar to threads but has a lot of differences 
like a supervisor actor, they don't have a shared memory which solves a lot of problems with concurency(not like threads).
And to interact somehow with each other, they use messages.


## Bibliography
https://www.youtube.com/watch?v=UmbBgy4mBxI
https://akka.io/
https://byjus.com/maths/prime-factorization/
https://www.scala-lang.org/

