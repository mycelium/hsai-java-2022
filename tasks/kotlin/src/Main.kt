data class Bools(
    var CheckFile: Boolean = false,
    var CheckDirectory: Boolean = false,
    var IgnoreRegister: Boolean = false
)
data class Vals(
    var AfterRows: Int = 0,
    var BeforeRows: Int = 0,
    var FilePath: String = "",
    var Pattern: String = "",
    var ListExclude: MutableList<String> = mutableListOf()
)
fun main(args: Array<String>) {
    val boolset = Bools()
    val valset = Vals()
    val reader = Reader()
    val process = Process(reader)
    process.Assigment(args, boolset, valset)
    if (boolset.CheckFile) process.FileProcessor(boolset, valset, reader)
    else process.DirectoryProcessor(boolset, valset, reader)
}