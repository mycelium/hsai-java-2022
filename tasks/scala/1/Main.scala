package recfun

object Main {
  def main(args: Array[String]): Unit = {
    /* Подзадача 1 */
    // println("Pascal's Triangle")
    // for (row <- 0 to 10) {
    //   for (col <- 0 to row)
    //     print(s"${pascal(col, row)} ")
    //   println()
    // }

    /* Подзадача 2 */
    // print(balance("()()(()())".toList))

    /* Подзадача 3 */
    print(countChange(19, List(1,2,5)))
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
    checkParentheses(chars.filter(c => (c == '(' || c == ')')), 0)
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

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    countChangeRecursive(money, coins.sorted(Ordering.Int.reverse))
  }

  def countChangeRecursive(money: Int, coins: List[Int]): Int = {
    if (money == 0) {
      1
    }
    else {
      val counts = 
        for (c <- coins) 
          yield if (money < c) 0 else countChange(money - c, coins.filter(_ <= c))
      counts.sum
    }
  }
}