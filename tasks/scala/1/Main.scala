package recfun
//import common._

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
    if ((c == r) || (c == 0))
      1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def countBrackets(chars: List[Char], openBrackets: Int): Boolean = {

      if (chars.isEmpty)
        openBrackets == 0
      else {
        val num = 0
        if (chars.head == '(')
          openBrackets + 1
        else if (chars.head == ')')
          openBrackets - 1
        else
          openBrackets
        if (num >= 0)
          countBrackets(chars.tail, num)
        else false
      }
    }
    countBrackets(chars, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    if ((money < 0) || (coins.isEmpty && money > 0))
      0
    countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }
}
