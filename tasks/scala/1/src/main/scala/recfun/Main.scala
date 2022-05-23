package recfun
//import common._

object Main {
  def main(args: Array[String]) {
    println("***Pascal's Triangle***")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }

    println("\n\n***Parentheses Balancing***")
    print(")( is ")
    println(balance(")(".toList))
    print("(()) is ")
    println(balance("(())".toList))
    print("(())()(() is ")
    println(balance("(())()(()".toList))
    print("( is ")
    println(balance("(".toList))
    print("() is ")
    println(balance("()".toList))

    println("\n\n***Counting Change***")
    val coins = List(1, 5, 10);
    println(countChange(17, coins));
  }


  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == r || c == 0) 1
    else pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def countBalance(chars: List[Char], c: Int): Boolean = {
      if (c < 0) return false
      if (chars.length == 0) c == 0
      else chars.head match {
        case '(' => countBalance(chars.tail, c + 1)
        case ')' => countBalance(chars.tail, c - 1)
        case _ => countBalance(chars.tail, c)
      }
    }

    countBalance(chars, 0)
  }


  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
      def countChangeRec(money: Int, coins: List[Int]): Int = {
      if (money < 0 || coins.isEmpty) return 0
      if (money == 0) return 1
      countChangeRec(money, coins.tail) + countChangeRec(money - coins.head, coins)
    }
    countChangeRec(money, coins)
  }
}

