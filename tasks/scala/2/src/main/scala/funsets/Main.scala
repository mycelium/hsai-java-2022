package funsets

object Main extends App {
  import FunSets._
  val s1 = singletonSet(2)
  val s2 = singletonSet(3)
  val s3 = singletonSet(4)
  val unionVar = union(s1, s2)
  val difference = diff(unionVar, s2)

  val three = union(unionVar, s3)


  printSet(map(three, v => v * 3))
}
