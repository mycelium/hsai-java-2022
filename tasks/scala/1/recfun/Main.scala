package recfun

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
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) return 1
    pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    @tailrec
    def helper(chars: List[Char], acc: Int = 0): Boolean = chars match {
      case Nil => acc == 0
      case '(' :: xs => helper(xs, acc + 1)
      case ')' :: xs => if (acc > 0) helper(xs, acc - 1) else false
      case _ :: xs => helper(xs, acc)
    }

    helper(chars)
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
      if (money == 0) 1
      else if ((money < 0) || coins.isEmpty) 0
      else helper(money, coins.dropRight(1)) + helper(money - coins.last, coins)
    }

    helper(money, coins)
  }
}
