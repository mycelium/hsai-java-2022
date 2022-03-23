package recfun
import common._

object Main {
  def main(args: Array[String]) = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    
    println(balance("((a))))".toList))
    val coins = List(1,5,10);
    print(countChange(17,coins));
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c==r || c==0){
        return 1;   
    } 
    if(c!=0){
        return pascal(c, r-1) + pascal(c-1,r-1);
    }
    else {
        return r;
    }
  }

  /** 
   * Exercise 2 Parentheses Balancing
    */
  def balance(chars: List[Char]): Boolean = {
    val opening = List( '(', '{', '[' );
    val closing = List( ')', '}', ']' );
          
    if(chars.length==0){
      return true;
    }
    if(chars.length==1 && (opening.indexOf(chars.head) > -1 ||closing.indexOf(chars.head) > -1)){
      return false;
    }
    val k = opening.indexOf(chars.head);
    if(k > -1){
      if(chars(chars.length-1)==closing(k)){
          val tmpList = chars.drop(1);
          return balance(tmpList.dropRight(1));
      }  
      else{
          return balance(chars.dropRight(1));
      }
    }
    else{
      return balance(chars.drop(1));
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
    if(money<0){
      return 0;
    }
    if(money==0 || coins.length==1){
      return 1;
    }
    return (countChange(money,coins.dropRight(1)) + countChange(money-coins(coins.length-1),coins));
  }
   
}
