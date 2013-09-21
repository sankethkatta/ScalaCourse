package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) {
      1
    } else {
      pascal(c, r-1) + pascal(c-1, r-1)
    }
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def innerBalance(idx: Int, count: Int): Boolean = {
      if (idx == chars.size) {

        if (count == 0) true
        else false

      } else { 
        if (chars(idx) == '(') {
          innerBalance(idx + 1, count + 1)
        } else if (chars(idx) == ')') {
          if (count == 0) false
          else innerBalance(idx + 1, count - 1)
        } else {
          innerBalance(idx + 1, count)
        }
      }
    }
    
    innerBalance(0, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
