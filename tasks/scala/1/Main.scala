package recfun
//import common._

object Main {
  def main(args: Array[String]) = {
   
    //Exercise 1
    
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    
    //Exercise 2

    println("\nParentheses Balancing")
    print("for \")(\" result is:")
    println(balance(")(".toList))
    print("for \"((a+b)*(c-d)))\" result is:")
    println(balance("((a+b)*(c-d)))".toList))
    print("for \"(((a-c)*(c/b))/(s))\" result is:")
    println(balance("(((a-c)*(c/b))/(s))".toList))

    //Exercise 3

    println("\nCounting Change")
    print("money:5,coins:{2,3}, result:")
    println(countChange(5,List(2,3)))
    print("money:5,coins:{1,2,3}, result:")
    println(countChange(5,List(1,2,3)))
    print("money:10,coins:{1,2,5}, result:")
    println(countChange(10,List(1,2,5)))
    print("money:10,coins:{1,2,4,5} result:")
    println(countChange(10,List(1,2,4,5)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(c == 0||c==r) 1
    else pascal(c-1,r-1) + pascal(c,r-1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def checkByCount(chars: List[Char], count:Int): Boolean = chars match{
      case '(' :: tail => checkByCount(tail, count + 1)
      case ')' :: tail => if(count == 0) false else checkByCount(tail, count - 1)
      case  _  :: tail => checkByCount(tail, count)
      case     Nil     => count == 0
    }
    checkByCount(chars, 0)
 }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def change(money: Int, coins: List[Int]): Int = {
      if(money == 0) 1
      else if(money < 0||coins.isEmpty) 0
      else {
        change(money-coins.head,coins)+change(money,coins.tail)
      }
    }
    change(money,coins.sorted)
  }
}
