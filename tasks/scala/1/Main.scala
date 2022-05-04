package scala.`1`
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

  println("\nParentheses Balancing")
  println(balance("(())(())".toList))
  println(balance("())(())".toList))
  println(balance(")(())())".toList))
  println(balance("".toList))

  println("\nCounting Change")
  println(countChange(5, List(2,3)))
  println(countChange(11, List(1,5)))
  println(countChange(7, List(1,2,3,5)))
  println(countChange(0, List(1,2,3,5)))

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) { 1 }
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def checkPattern(chars: List[Char], counter: Int): Boolean = chars match {
      case '(' :: tail => checkPattern(tail, counter + 1)
      case ')' :: tail => checkPattern(tail, counter - 1)
      case _ :: tail => checkPattern(tail, counter)
      case Nil => counter == 0
    }

    checkPattern(chars, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = (money, coins) match {
    case (0, _) => 1
    case (_, Nil) => 0
    case (money, _) if money < 0 => 0
    case (money, coin :: coins) =>
      val perms =
        for (n <- 0 to (money / coin))
          yield countChange(money - (n * coin), coins)
      perms.sum
  }
}
