package recfun

object Main {
  def main(args: Array[String]): Unit = {
    // println("Pascal's Triangle")
    // for (row <- 0 to 10) {
    //   for (col <- 0 to row)
    //     print(s"${pascal(col, row)} ")
    //   println()
    // }

    print(balance("()()(()())".toList))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (r == 0 || c == 0 || c == r) { 1 }
    else { pascal(c-1, r-1) + pascal(c, r-1) }
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    checkParentheses(chars.filter(c => (c != '(' || c != ')')), 0)
  }

  def checkParentheses(chars: List[Char], stack: Int): Boolean = {
    if (chars.size == 0) { stack == 0 }
    else if (stack < 0) { false }
    else if (chars.head == '(') {
      checkParentheses(chars.drop(1), stack+1)
    }
    else if (chars.head == ')') {
      checkParentheses(chars.drop(1), stack-1)
    }
    else false
  }

  //   /**
  //    * Exercise 3 Counting Change
  //    * Write a recursive function that counts how many different ways you can make
  //    * change for an amount, given a list of coin denominations. For example,
  //    * there is 1 way to give change for 5 if you have coins with denomiation
  //    * 2 and 3: 2+3.
  //    */
  //   def countChange(money: Int, coins: List[Int]): Int = {

  //   }
}