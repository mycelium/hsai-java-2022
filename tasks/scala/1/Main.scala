object Main {
  def main(args: Array[String]) = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row) {
        print(pascal(col, row) + " ")
      }
      println()
    }

    println(balance("()()".toList))
    val coins = List(1, 5, 10);
    print(countChange(17, coins));
  }

  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) { return 1; }
    else { return pascal(c - 1, r - 1) + pascal(c, r - 1); }
  }

  def balance(chars: List[Char]): Boolean = {
    def calculate(chars: List[Char], counter: Int): Boolean = {
      if (chars.isEmpty) { return counter == 0; }
      if (chars.head == '(') { return calculate(chars.tail, counter + 1); }
      if (chars.head == ')') { return calculate(chars.tail, counter - 1); }
      else { return calculate(chars.tail, counter); }
    }
    return calculate(chars, 0);
  }


  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0) { return 0; }
    if (money == 0 || coins.length == 1) { return 1; }
    return (countChange(money, coins.dropRight(1)) + countChange(money - coins.last, coins));
  }

}
