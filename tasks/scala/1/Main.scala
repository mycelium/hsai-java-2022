package recfun

//import common._
import collection.mutable.Map

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }

    println(balance("[(([])]{})".toList))

    val coins = List(1, 5, 10)
    print(countChange(17, coins))
//    print(countChangeDP(17, coins))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == r | c == 0) {
      1
    } else {
      pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def balanced(chars: List[Char], open: Int): Boolean =
      if (chars.isEmpty) open == 0
      else if (chars.head == '(') balanced(chars.tail, open + 1)
      else if (chars.head == ')') open > 0 && balanced(chars.tail, open - 1)
      else balanced(chars.tail, open)
    balanced(chars, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */

  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0 | coins.isEmpty) {
      0
    } else if (money == 0) {
      1
    } else {
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }

  def countChangeDP(money: Int, coins: List[Int]): Int = {
    val combinations: Array[Int] = new Array[Int](money + 1)

    combinations(0) = 1

    for (coin <- coins) {
      for (i <- coin to money) {
        if (i >= coin) {
          combinations(i) += combinations(i - coin)
        }
      }
    }

    combinations(money)
  }
}
