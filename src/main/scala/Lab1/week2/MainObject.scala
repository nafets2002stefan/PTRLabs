package Lab1.week2

import scala.math._
import scala.util.Random

object MainObject {

  def isPrime(number: Int): Boolean = {
    val temp = sqrt(number).toInt
    for(i <- 2 to  temp) {
      if(number % i == 0)
        return false
    }
    true
  }

  def cylinderArea(height : Int, radius : Int): Double = {
    val sum = height + radius
    val result = 2 * Pi * radius * sum
    result
  }

  def reverse(list: Array[Int]):Array[Int] = {
    var reversedList: Array[Int] = Array.empty[Int]
    for(i <- list.length-1 to 0 by -1)
      reversedList = reversedList :+ list(i)
    reversedList
  }

  def uniqueSum(list: Array[Int]): Int = {
    var uniqueValues: Set[Int] = Set()
    for(i <- 0 until list.length)
      uniqueValues += list(i)
    uniqueValues.sum
  }

  def extractRandomN(list: Array[Int], count: Int) : Array[Int] = {
    var finalList = Array.empty[Int]
    val rand = new Random()
    for(i <- 0 to count-1) {
      val randomIndex : Int = rand.nextInt(list.length)
      finalList = finalList :+ list(randomIndex)
    }
    finalList
  }

  def firstFibonacciElements(numberOfElements: Int) : Array[Int] = {
    var list = Array.empty[Int]

    def fibonacciHelp(n : Int): Int = {
      if(n <= 1) {
        return n
      }
    fibonacciHelp(n-1) + fibonacciHelp(n - 2)
    }

    for(i <- 0 to numberOfElements-1)
      list = list :+ fibonacciHelp(i)
    list
  }

  def translator(dictionary: Map[String, String], originalString: String) : String = {
    val wordsArray = originalString.split(" ")
    val changedArray = wordsArray.map(word => dictionary.getOrElse(word, word))
    val finalResult = changedArray.mkString(" ")

    finalResult
  }

  def smallestNumber(nrOne: Int, nrTwo: Int, nrThree: Int): String = {
    var finalNumber = ""
    val sortedNumbers = Seq(nrOne, nrTwo, nrThree).sorted
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
    finalNumber
  }

  def rotateLeft[A](list:List[A],rotationLength: Int): List[A] = {
    val rotation = rotationLength % list.length
    val finalList = list.drop(rotation) ++ list.take(rotation)

    finalList
  }

  def listRightAngleTriangles(): Array[(Int, Int, Int)] = {
    for {
      a <- 1 to 20
      b <- 1 to 20
      c = math.sqrt(a * a + b * b).toInt
      if c <= 20 && pow(c, 2) == pow(a, 2) + pow(b, 2)
    } yield (a, b, c)
  }.toArray

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

  def lineWords(list: Array[String]) : Array[String] = {
    var finalList = Array[String]()

    val row1 = Array("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
    val row2 = Array("a", "s", "d", "f", "g", "h", "j", "k", "l")
    val row3 = Array("z", "x", "c", "v", "b", "n", "m")


    for(word <- list) {

      var boolRow1 = true
      var boolRow2 = true
      var boolRow3 = true

      for(letter <- word.toLowerCase()) {
        if(!row1.contains(letter.toString))
          boolRow1 = false
        if (!row2.contains(letter.toString))
          boolRow2 = false
        if (!row3.contains(letter.toString))
          boolRow3 = false
      }
      if(boolRow1 || boolRow2 || boolRow3)
        finalList = finalList :+ word
    }
    finalList
  }

  def encode(word : String, move : Int ) : String = {
    var encoded = ""
    for(ch <- word) {
      val temp = ch.toInt + move
      encoded += temp.toChar
    }

    encoded
  }

  def decode(word: String, move: Int): String = {
    var decoded = ""
    for (ch <- word) {
      val temp = ch.toInt - move
        decoded += temp.toChar
    }

    decoded
  }

  def lettersCombinations(number : String) : List[String] = {
    val phoneMap = Map(
      '2' -> "abc",
      '3' -> "def",
      '4' -> "ghi",
      '5' -> "jkl",
      '6' -> "mno",
      '7' -> "pqrs",
      '8' -> "tuv",
      '9' -> "wxyz")

    def recursionHelper(finalString: String, remaining : String) : List[String] = {
      if(remaining.isEmpty)
        List(finalString)
      else {
        phoneMap(remaining.head).flatMap(c => recursionHelper(finalString + c, remaining.tail)).toList
      }
    }

    if(number.isEmpty)
      List.empty[String]
    else
      recursionHelper("", number)
  }

  def groupAnagrams(list: List[String]) : Map [String, List[String]] = {
    var finalMap = Map.empty[String, List[String]]

    for(el <- list) {
        val sorted = el.toSeq.sorted.mkString
        if(finalMap.contains(sorted)) {
            finalMap += (sorted -> (finalMap(sorted) :+ el))
        }
        else {
          finalMap += (sorted -> List(el))
        }

    }
    finalMap
  }

  def commonPrefixHelper(firstString: String, secondString: String): String = {
    var result = ""
    val minimalString = min(firstString.length, secondString.length)

    for (i <- 0 until minimalString) {
      if (firstString(i) == secondString(i))
        result += firstString(i)
      else {
        return result
      }
    }
    result
  }

  def commonPrefix(list: List[String]) : String = {

    if(list.isEmpty) ""
    else {
      list.reduce((firstString, secondString) => commonPrefixHelper(firstString, secondString))
    }
  }

  def toRoman(number : String) : String = {
    val rules = List(
      ("M", 1000),
      ("CM", 900),
      ("D", 500),
      ("CD", 400),
      ("C", 100),
      ("XC", 90),
      ("L", 50),
      ("XL", 40),
      ("X", 10),
      ("IX", 9),
      ("V", 5),
      ("IV", 4),
      ("I", 1))

    var result = ""
    var calculator = number.toInt

    for((roman, arabic) <- rules)
      while (calculator >= arabic) {
        result += roman
        calculator -= arabic
      }
    result
  }

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
//    println(removeConsecutiveDuplicates(Array(5 , 11 , 3 , 3 , 2 , 11 , 5, 12)).mkString(", "))
//    println(lineWords(Array("peace", "alaska", "dad", "peace")).mkString(", "))
//    println(encode("lorem", 3))
//    println(decode("oruhp", 3))
//    println(lettersCombinations("23"))
    println(groupAnagrams(List(" eat " , " tea " , " tan " , " ate " , " nat " , " bat ")))
//    println(commonPrefix(List(" fleight " ," fleio " ," fleo ")))
//    println(toRoman("19"))
//    println(factorize(42))
  }

}
