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
  /*  println("------------------------")
    println("Exercise 2 Parentheses Balancing")
    println(balance(")(".toList))
    println(balance("())".toList))
    println(balance("))())".toList))

    println("------------------------")
    println("Exercise 3 Counting Change")
    println(countChange(10, List(5, 2, 3)))
    println(countChange(10, List()))
    println(countChange(0, List(50, 10)))*/
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) {1}
    else {
      pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def countParentheses(chars: List[Char], cnt: Int): Boolean = chars match {
      case Nil => cnt == 0
      case '(' :: tail => countParentheses(tail, cnt + 1)
      case ')' :: tail => countParentheses(tail, cnt - 1)
      case _ :: tail => countParentheses(tail, cnt)
      }

    countParentheses(chars, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money <= 0 || coins.isEmpty) {
      if (money == 0) {1}
      else {0}
    }
    else {
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }
}
