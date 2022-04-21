object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    println("Parentheses Balancing")
    println(balance("()()()()((((()".toList))
    println("Counting Change")
    print(countChange(5, List(2,3)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == r || c == 0) return 1
    pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    checkBrackets(chars, 0) == 0;
  }
  def checkBrackets(chars: List[Char], n: Int): Int = chars match {
    case ')' :: rest => if (n < 1) -1 else checkBrackets(rest, n - 1)
    case '(' :: rest => checkBrackets(rest, n + 1)
    case _ :: rest => checkBrackets(rest, n)
    case Nil => n
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    countingOptions(money, coins)
  }
  def countingOptions(money: Int, coins: List[Int]): Int ={
    if (money < 0 || coins.isEmpty) 0
    else if (money == 0) 1
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }
}
