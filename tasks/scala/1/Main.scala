package recfun
import common._

object Main {
  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   * Not memoizing or tailrec for simplicity sake
   */
  def pascal(c: Int, r: Int): Int = (c, r) match {
    case (0, _) => 1
    case (c, r) if c == r => 1
    case (c, r) => pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    val <> : (List[Char], Char) => List[Char] = {
      case ('(' :: xs, ')') => xs
      case ('[' :: xs, ']') => xs
      case ('{' :: xs, '}') => xs
      case (xs, x) => x :: xs
    }

    chars.foldLeft(List[Char]())(<>).isEmpty
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
