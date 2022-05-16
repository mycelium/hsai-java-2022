object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }

    println("Parentheses Balancing")
    val goodExpression = "(a+b)-(c*(a-b)^d)"
    val wrongExpression = "(aa(bb-cc)))))"
    println(goodExpression + " is " + (if (balance(goodExpression.toList)) "balanced" else "unbalanced"))
    println(wrongExpression + " is " + (if (balance(wrongExpression.toList)) "balanced" else "unbalanced"))

  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def isBalanced(chars: List[Char], opened: Int): Boolean = {
      if (chars.isEmpty) opened == 0
      else {
        chars.head match {
          case '(' => isBalanced(chars.tail, opened + 1)
          case ')' => isBalanced(chars.tail, opened - 1)
          case _ => isBalanced(chars.tail, opened)
        }
      }
    }
    isBalanced(chars, 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    return 1
  }
}
