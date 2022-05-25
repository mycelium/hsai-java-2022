package recfun

import scala.collection.mutable
//import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }



    //2

    println(balance(")(".toList))
    println(balance("(2-5*(456)))".toList))
    println(balance("((fadfaf*(fdaf(a)d))/(34))".toList))
    println(balance("(".toList))
    println(balance(")".toList))
    println(balance("()".toList))
    println(balance("".toList))




    //3



    print("money: 5; coins: 2, 3; result:")
    println(countChange(5, List(2, 3)))
    print("money: 5; coins: 1, 2, 3; result:")
    println(countChange(5, List(1, 2, 3)))
    print("money: 15; coins: 1, 2, 5; result:")
    println(countChange(15, List(1, 2, 5)))
    print("money: 22; coins: 1, 2, 3, 4, 5; result:")
    println(countChange(22, List(1, 2, 3, 4, 5)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(c == 0 || c == r)
      1
    else
      pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    val st = new mutable.Stack[Char]()
    for ( i <- 0 to (chars.length - 1)) {
      if (chars(i) == '(') {
        st.push(chars(i))
      }
      if(chars(i) == ')'){
        if(st.isEmpty)
          return false
        else
          st.pop()
      }
    }
    if(st.isEmpty)
      true
    else
      false
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(money == 0)
      1
    else if(money < 0 || coins.isEmpty)
      0
    else {
      countChange(money-coins.head,coins)+countChange(money,coins.tail)
    }
  }
}