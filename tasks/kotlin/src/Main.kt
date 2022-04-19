data class Flags(
    var fileCheck: Boolean = false,
    var catalogCheck: Boolean = false,
    var ignoreRegister: Boolean = false
)
data class Values(
    var path: String = "",
    var pattern: String = "",
    var afterContext: Int = 0,
    var beforeContext: Int = 0,
    var exclude: MutableList<String> = mutableListOf()
)
fun main(args: Array<String>) {
    val flags = Flags()
    val values = Values()
    Process.initialize(args, flags, values)
    if (flags.fileCheck) Process.fileProcessor(flags, values)
    else Process.catalogProcessor(flags, values)
}