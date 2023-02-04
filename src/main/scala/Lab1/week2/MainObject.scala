package Lab1.week2

import java.util.Dictionary
import scala.math._
import scala.util.Random

object MainObject {

  def isPrime(number: Int): Boolean = {
    var temp = sqrt(number).toInt
    for(i <- 2 to  temp) {
      if(number % i == 0)
        return false
    }
    return true
  }

  def cylinderArea(height : Int, radius : Int): Double = {
    var sum = height + radius;
    var result = 2 * Pi * radius * sum;
    return result;
  }

  def reverse(list: Array[Int]):Array[Int] = {
    var reversedList: Array[Int] = Array.empty[Int]
    for(i <- list.length-1 to 0 by -1)
      reversedList = reversedList :+ list(i)
    return reversedList
  }

  def uniqueSum(list: Array[Int]): Int = {
    var uniqueValues: Set[Int] = Set()
    for(i <- 0 to list.length - 1)
      uniqueValues += list(i)
    return uniqueValues.sum
  }

  def extractRandomN(list: Array[Int], count: Int) : Array[Int] = {
    var finalList = Array.empty[Int]
    var rand = new Random()
    for(i <- 0 to count-1) {
      var randomIndex : Int = rand.nextInt(list.length)
      finalList = finalList :+ list(randomIndex)
    }
    return finalList
  }

  def firstFibonacciElements(numberOfElements: Int) : Array[Int] = {
    var list = Array.empty[Int]

    def fibonacciHelp(n : Int): Int = {
      if(n <= 1) {
        return n
      }
    return fibonacciHelp(n-1) + fibonacciHelp(n - 2)
    }

    for(i <- 0 to numberOfElements-1)
      list = list :+ fibonacciHelp(i)
    return list
  }

  def translator(dictionary: Map[String, String], originalString: String) : String = {
    var wordsArray = originalString.split(" ")
    var changedArray = wordsArray.map(word => dictionary.getOrElse(word, word))
    var finalResult = changedArray.mkString(" ")

    return finalResult
  }

  def smallestNumber(nrOne: Int, nrTwo: Int, nrThree: Int): String = {
    var finalNumber = ""
    var sortedNumbers = Seq(nrOne, nrTwo, nrThree).sorted
    if(sortedNumbers(0) == 0) {
      if(sortedNumbers(1) != 0) {
        finalNumber += sortedNumbers(1)
        finalNumber += sortedNumbers(0)
        finalNumber += sortedNumbers(2)
      }
      else {
        finalNumber += sortedNumbers(2)
        finalNumber += sortedNumbers(0)
        finalNumber += sortedNumbers(1)
      }
    }
    else {
      finalNumber += sortedNumbers(0)
      finalNumber += sortedNumbers(1)
      finalNumber += sortedNumbers(2)
    }
    return finalNumber
  }

  def rotateLeft[A](list:List[A],rotationLength: Int): List[A] = {
    var rotation = rotationLength % list.length
    var finalList = list.drop(rotation) ++ list.take(rotation)

    return finalList
  }

  def listRightAngleTriangles(): Array[(Int, Int, Int)] = {
    for {
      a <- 1 to 20
      b <- 1 to 20
      c = math.sqrt(a * a + b * b).toInt
      if (c <= 20 && pow(c, 2) == pow(a, 2) + pow(b, 2))
    } yield (a, b, c)
  }.toArray

  def main(args: Array[String]): Unit = {
//    println(isPrime(8))
//    println(cylinderArea(3,4))
//    println(reverse(Array(1,2,3)).mkString(","))
//    println(uniqueSum(Array(1,2,3,1,3,4)))
//    println(extractRandomN(Array(8, 8, 9 ,2, 1), 3).mkString(","))
//    println(firstFibonacciElements(40).mkString(","))
//    println(translator(Map("mama" -> "mother", "papa" -> "father"), "mama is with papa"))
//    println(smallestNumber(3, 2, 0))
//    println(rotateLeft(List(1, 2, 4, 8, 4), 3))
//    println(listRightAngleTriangles().mkString(", "))

  }

}
