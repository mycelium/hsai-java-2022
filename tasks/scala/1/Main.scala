import common._

object Main {
  def main(args: Array[String]) {
    // TASK 3
    print(countChange(19, List(2,3,5))
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
    def checkRemainingParentheses(chars: List[Char], count: Int): Boolean = {
      if (chars.length == 0) { count == 0 }
      else if (count < 0) false
      else if (chars.head == '(') checkRemainingParentheses(chars.drop(1), count+1)
      else if (chars.head == ')') checkRemainingParentheses(chars.drop(1), count-1)
      else false
    }
    checkRemainingParentheses(chars.filter(c => c == '(' || c == ')'), 0)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) return 1
    if (coins.length == 0 || money < 0) return 0
    var count = 0
    for (c <- coins) {
      if (money >= c) {
        count += countChange(money - c, coins.filter(_ <= c))
      }
    }
    count
  }
}
