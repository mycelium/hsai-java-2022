package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  testFunSets()

  def testFunSets() {
    val set1: Set = x => (x > 0) && (x < 7)
    val set2: Set = x => (x > 4) && (x < 9)

    printSet(set1)
    printSet(set2)

    printSet(union(set1, set2))
    printSet(intersect(set1, set2))
    printSet(diff(set1, set2))
    printSet(filter(set1, x => x > 3))
    println(forall(set1, x => x > 3))
    println(exists(set1, x => x > 3))
    printSet(map(set1, x => x + 3))
  }
}
