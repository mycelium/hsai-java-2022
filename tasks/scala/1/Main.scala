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

    println("\nParentheses Balancing")
    println(balance(")(".toList))
    println(balance("()w(ord)".toList))

    println("\nCounting Change")
    println(countChange(5, List(2, 3)))
    println(countChange(5, List(1, 2)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def recursiveBalance(chars: List[Char], count: Int): Boolean = {
      if (count < 0) return false
      if (chars.isEmpty) count == 0
      else
        chars.head match {
          case '(' => recursiveBalance(chars.tail, count + 1)
          case ')' => recursiveBalance(chars.tail, count - 1)
          case _ => recursiveBalance(chars.tail, count)
        }
    }

    recursiveBalance(chars, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def recursiveChange(money: Int, coins: List[Int]): Int = {
      if (money < 0 || coins.isEmpty) return 0
      if (money == 0) return 1
      recursiveChange(money, coins.tail) + recursiveChange(money - coins.head, coins)
    }

    recursiveChange(money, coins)
  }
}
}
