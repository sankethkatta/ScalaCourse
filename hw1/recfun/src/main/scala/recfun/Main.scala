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
  def countChange(money: Int, coins: List[Int]): Int = {

    def innerLoop(numWays: Int, moneyRemain: Int, curCoin: Int, remain: List[Int]): Int = {
      if (moneyRemain % curCoin == 0) {
        if (remain.isEmpty) numWays + 1
        else innerLoop(numWays + 1, money, remain.head, remain.tail)
      } else {
        if (remain.isEmpty) numWays
        else {
          if (moneyRemain - curCoin > 0) 
            innerLoop(numWays, moneyRemain - curCoin, remain.head, remain.tail)
          else 
            numWays
        }
      }
    }

    if (coins.isEmpty) {
      0
    } else {
      innerLoop(0, money, coins.head, coins.tail)
    }

  }
}
