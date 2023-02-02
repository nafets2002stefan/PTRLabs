import Lab1.week1.HelloPTR
import org.scalatest.funsuite.AnyFunSuite

import java.io.{ByteArrayOutputStream, PrintStream}

//Compare message which was printed on screen with "Hello PTR"
  class DisplayHelloPtrTest extends AnyFunSuite {
    test("HelloPTR.main") {
      val result = new ByteArrayOutputStream()
      Console.withOut(new PrintStream(result)) {
        HelloPTR.main(Array.empty[String])
      }
      assert(result.toString == "Hello PTR")
    }
  }
