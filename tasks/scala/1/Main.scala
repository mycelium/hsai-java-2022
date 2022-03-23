package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    //test bracket balance (true)
    println(balance("{([])}".toList));
    //test bracket balance (false)
    println(balance("{(})".toList));
    //test coins change
    println(countChange(5, List(2, 3)))
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
    val bracketsMap = Map(
      ')' -> '(', '{' -> '}', '[' -> ']'
    )

    def balanced(chars: List[Char], open: Int): Boolean = {
      if (open < 0) false else
        chars match {
          case Nil => open == 0
          case _ => {
            chars.head match {
              case in if bracketsMap.keys.toSeq.contains(in) => balanced(chars.tail, open + 1)
              case in if bracketsMap.values.toSeq.contains(in) => balanced(chars.tail, open - 1)
              case _ => balanced(chars.tail, open)
            }
          }
        }
    }

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
    if (money < 0 || coins.isEmpty) 0
    else if (money == 0) 1
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }
}