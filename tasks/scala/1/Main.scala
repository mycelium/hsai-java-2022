package recfun
import common._

import scala.annotation.tailrec

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
  def pascal(c: Int, r: Int): Int = c match {
    case 0 => 1
    case a if a == r => 1
    case _ => pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {

    @tailrec
    def helper(chars: List[Char], countOpen: Int): Boolean = chars match {
      case Nil => countOpen == 0
      case '(' :: xs => helper(xs, countOpen + 1)
      case ')' :: xs => if (countOpen > 0) helper(xs, countOpen - 1) else false
      case _ :: xs => helper(xs, countOpen)
    }

    helper(chars, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    def helper(money: Int, coins: List[Int]): Int = {
      if (money == 0) return 1
      if ((money < 0) || coins.isEmpty) return 0

      // 1 - case without the biggest coin
      // 2 - case where we put the biggest coin
      helper(money, coins.dropRight(1)) + helper(money - coins.last, coins)
    }

    helper(money, coins.distinct.sorted)
  }
}
