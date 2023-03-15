package Lab1.week5

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext.Implicits.global
import org.jsoup.Jsoup
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.util.ByteString
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`

object MinimalTask {
  def main(args: Array[String]): Unit = {
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

          val document = Jsoup.parse(body.utf8String)

          val quotes = document.select(".quote").map { quoteElement =>
            val author = quoteElement.select(".author").text()
            val text = quoteElement.select(".text").text()
            val tags = quoteElement.select(".tags .tag").map(_.text()).toList
            Map("author" -> author, "text" -> text, "tags" -> tags)
          }.toList

          println(s"Extracted ${quotes.size} quotes:")
          quotes.foreach(quote => println(s"Author: ${quote("author")}, Text: ${quote("text")}, Tags: ${quote("tags")}"))

          val mapper = new ObjectMapper().registerModule(DefaultScalaModule)
          val json = mapper.writeValueAsString(quotes)
          java.nio.file.Files.write(java.nio.file.Paths.get("quotes.json"), json.getBytes())
          println("Quotes saved to file 'quotes.json'")
        }
      }
      .onComplete(_ => system.terminate())
  }

}