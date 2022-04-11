package recfun
import common._

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
  if(c==0||c==r) 1
  else pascal(c-1,r-1) +  pascal(c,r-1)
  }

  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
   def helper(chars:List[Char],open:Int,close:Int):Boolean = {
              if(chars.isEmpty==false) {       
                     if (chars.head == '(') {      
                         var newchar = chars.tail      
                         helper(newchar,open+1,close)      
                       }  
              else if (chars.head == ')' ) {  
                   var newchar = chars.tail  
                   helper(newchar,open,close+1)  
                      }  
              else {    
                  var newchar = chars.tail    
                  helper(newchar,open,close)  
                  }          
            }        
            else {      
              open==closed        
             }         
        } 
helper(chars,0,0)    
}

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = {
   def countChange(money:Int, coins: List[Int], count:Int): Int = {
      if (money < 0) count
      else
        if (coins.isEmpty) 
          if (money == 0) count + 1 else count
        else
          countChange(money - coins.head, coins, count) + countChange(money, coins.tail, count)
    }
    countChange(money, coins, 0)

  }
}
