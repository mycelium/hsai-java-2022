package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))

  def test(): Unit = {
    val set1: Set = el => (el > 0) && (el < 5)
    val set2: Set = el => (el > 3) && (el < 8)

    printSet(set1)
    printSet(set2)

    printSet(union(set1, set2))
    printSet(intersect(set1, set2))
    printSet(diff(set1, set2))
    printSet(filter(set1, el => el < 3))

    println(forall(set1, el => el < 6))
    println(forall(set2, el => el < 6))

    println(exists(set1, el => el < 6))
    println(exists(set2, el => el < 6))

    printSet(map(set1, el => el * 2))
  }
}
