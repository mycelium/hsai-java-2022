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
    if (c == 0 || r == c) {
      return 1
    }
    pascal(c - 1, r - 1) + pascal(c, r - 1)
  }


  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def bracketsCounting(chars: List[Char], count: Int): Boolean = {
      if (chars.isEmpty) count == 0
      else if ("(".contains(chars.head)) bracketsCounting(chars.tail, count + 1)
      else if (")".contains(chars.head)) count > 0 && bracketsCounting(chars.tail, count - 1)
      else bracketsCounting(chars.tail, count)
    }

    bracketsCounting(chars, 0)
  }


  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */

  def countChange(money: Int, coins: List[Int]): Int = {
    def count(m: Int, coi: List[Int]): Int = {
      if (m <= 0) {
        if (m == 0) {
          1
        } else {
          0
        }
      } else {
        if (coi.isEmpty) {
          0
        } else {
          if (m - coi.head == 0) {
            1
          } else {
            if (m - coi.head < 0) {
              0
            } else {
              countChange(m - coi.head, coi) + countChange(m, coi.tail)
            }
          }
        }
      }
    }

    count(money, coins.sorted)
  }

}
