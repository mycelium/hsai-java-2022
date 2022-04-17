import common._

object Main {
  def main(args: Array[String]) {
//    for (row <- 0 to 10) {
//      for (col <- 0 to row)
//        print(pascal(col, row) + " ")
//      println()
//    }

//    print(balance("()()()(())".toList))

    print(countChange(10, List(2,3,1)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == r || c == 0) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def checkParentheses(chars: List[Char], count: Int): Boolean = {
      if (chars.length == 0) { count == 0 }
      else if (count < 0) {
        false
      }
      else if (chars.head == ')') {
        checkParentheses(chars.drop(1), count-1)
      }
      else if (chars.head == '(') {
        checkParentheses(chars.drop(1), count+1)
      }
      else false
    }
    checkParentheses(chars.filter(c => c == '(' || c == ')'), 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0 || coins.isEmpty) { return 1 }
    var count = 0
    for (coin <- coins) {
      if (money >= coin) {
        count += countChange(money - coin, coins.filter(_ <= coin))
      }
    }
    return count
  }
}
