package recfun
//import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    println(balance("()()()()())".toList))
    println(countChange(12,List(2,5,3)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = if(c==0 || c==r) 1 else if(c<0 || c>r) 0 else pascal(c-1,r-1)+pascal(c,r-1)
  
  /**
   * Exercise 2 Parentheses Balancing
   */
   def balance(chars: List[Char]): Boolean = {
     def helper(count:Int, hchars: List[Char]): Boolean = hchars match{
       case Nil => count==0
       case '('::rest => helper(count+1, rest)
       case ')'::rest => if(count>0) helper(count-1, rest) else false
       case _::rest => helper(count, rest)
     }
     helper(0, chars)
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    var res=0
    def helper(re:Int, rc:List[Int]){
      if(re!=0){
        var aval=if(rc.isEmpty) return else rc.head
        if(re>=aval) {
        helper(re-rc.head,rc) 
        helper(re,rc.tail)
        }
      }
      else res=res+1
    }
    helper(money, coins)
    return res
  }
}