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
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r)
      1
    else
      pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    @tailrec //function is tail-recursive
    def parBalance(pos: Int, braces: Int): Boolean = {
      if (pos == chars.length)
        braces == 0
      else if (chars(pos) == '(')
        parBalance(pos + 1, braces + 1)
      else if (chars(pos) == ')')
        if (braces == 0)
          false
        else
          parBalance(pos + 1, braces - 1)
      else
        parBalance(pos + 1, braces)
    }

    parBalance(0, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coin: List[Int]): Int = {
    if (money == 0)
      1
    else if (money < 0 || coin.isEmpty)
      0
    else
      countChange(money - coin.head, coin) + countChange(money, coin.tail)
  }
}
