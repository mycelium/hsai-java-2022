package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }

    println(balance("{([])}".toList));
    println(balance("{(})".toList));

    println(countChange(5, List(2, 3)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = (c, r) match {
      case _ if c == r || c == 0 => 1
      case _ => pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  case class BracketsCount(round: Int, square: Int, curly: Int)
  /**
   * Exercise 2 Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def balanceImpl(chars: List[Char], bracketsStack: List[Char]): Boolean = {
    (chars, bracketsStack) match {
      case (x::xs, _) if x != ')' && x != ']' && x != '}' => balanceImpl(xs, x::bracketsStack) 
      case (l::xs, r::bxs) if l == ')' && r == '(' => balanceImpl(xs, bxs)
      case (l::xs, r::bxs) if l == ')' && r != '(' => false
      case (l::xs, r::bxs) if l == ']' && r == '[' => balanceImpl(xs, bxs)
      case (l::xs, r::bxs) if l == ']' && r != '[' => false
      case (l::xs, r::bxs) if l == '}' && r == '{' => balanceImpl(xs, bxs)
      case (l::xs, r::bxs) if l == '}' && r != '{' => false
      case (Nil, Nil) => true
      case _ => false
    }
  }
    return balanceImpl(chars, List())
  }

  /**
   * Exercise 3 Counting Change
   * Write a recursive function that counts how many different ways you can make
   * change for an amount, given a list of coin denominations. For example,
   * there is 1 way to give change for 5 if you have coins with denomiation
   * 2 and 3: 2+3.
   */
  def countChange(money: Int, coins: List[Int]): Int = (money, coins) match {
    case (0, _) => 1
    case (_, c::xs) if money >= c => countChange(money - c, xs) + countChange(money, xs)
    case (_, c::xs) if money < c => countChange(money, xs)
    case (_, _) => 0
  }
}
