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
    //println(countChange(4, List(1,2,3)))
    //println(balance("([(({)})])".toList))
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
    def balanced(chars: List[Char], open1: Int, open2: Int, open3: Int) : Boolean = {
      if (open1 < 0 || open2 < 0 || open3 < 0) false
      else
        if (chars.isEmpty) {
          open1 == 0 && open2 == 0 && open3 == 0
        }
        else
          chars.head match {
            case '(' => balanced(chars.tail, open1 + 1, open2, open3)
            case '{' => balanced(chars.tail, open1, open2 + 1, open3)
            case '[' => balanced(chars.tail, open1, open2, open3 + 1)
            case ')' => balanced(chars.tail, open1 - 1, open2, open3)
            case '}' => balanced(chars.tail, open1, open2 - 1, open3)
            case ']' => balanced(chars.tail, open1, open2, open3 - 1)
            case _ => balanced(chars.tail, open1, open2, open3)
          }
    }
    balanced(chars, 0, 0, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (coins.isEmpty) 0
    else
      if (money < 0) 0
      else
        if (money == 0) 1
        else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
