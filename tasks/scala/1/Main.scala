package recfun

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }

    //    println(balance("((my)dear)[sad{good}]".toList)) //Balance check
    //    println(balanceRec("((my)dear)[sad{good}]".toList)) //Recursive balance check
    //    println(countChange(5,List(2,3,1))) //Change check
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == r | c == 0) {
      1
    } else {
      pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  //Non-recursive
  def balance(chars: List[Char]): Boolean = {
    chars.count(_ == '(') == chars.count(_ == ')') |
      chars.count(_ == '[') == chars.count(_ == ']') |
      chars.count(_ == '{') == chars.count(_ == '}')
  }

  //Recursive
  def balanceRec(chars: List[Char]): Boolean = {
    val start = List('(', '[', '{')
    val end = List(')', ']', '}')

    @tailrec
    def innerCounter(lead: Map[Char, Int], index: Int): Boolean = {
      if (index == chars.length) {
        if (lead.values.forall(_ == 0)) {
          true
        } else {
          false
        }
      } else if (start.contains(chars(index))) {
        innerCounter(lead.updatedWith(chars(index)) {
          case Some(cnt) => Some(cnt + 1)
          case None => None
        }, index + 1)
      } else if (end.contains(chars(index))) {
        if (lead.getOrElse(start(end.indexOf(chars(index))), 0) == 0) {
          false
        } else {
          innerCounter(lead.updatedWith(start(end.indexOf(chars(index)))) {
            case Some(cnt) => Some(cnt - 1)
            case None => None
          }, index + 1)
        }
      } else {
        innerCounter(lead, index + 1)
      }
    }

    if (chars.isEmpty) {
      true
    } else {
      innerCounter(Map('(' -> 0, '[' -> 0, '{' -> 0), 0)
    }
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0 | coins.isEmpty) {
      0
    } else if (money == 0) {
      1
    } else {
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }
}

