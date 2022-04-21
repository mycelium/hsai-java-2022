import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

data class ArgumentSet(val args: Array<String>) {
    private val parser = ArgParser("grek")
    val lineNum: Boolean by parser.option(ArgType.Boolean, "line-number", "n", "print line number with output lines").default(false)
    val recursive: Boolean by parser.option(ArgType.Boolean, "recursive", "r", "search recursively in folder").default(false)
    val ignoreCase: Boolean by parser.option(ArgType.Boolean, "ignore-case", "i", "ignore case when searching").default(false)
    val exclude: String by parser.option(ArgType.String, fullName = "exclude", description = "skip files and directories matching the pattern").default("")
    val before: Int by parser.option(ArgType.Int, "before-context", "B", "print lines of leading context").default(0)
    val after: Int by parser.option(ArgType.Int, "after-context", "A", "print lines of trailing context").default(0)
    val pattern: String by parser.argument(ArgType.String, description = "regex pattern used in search")
    val path: String by parser.argument(ArgType.String, description = "path to file")

    init {
        parser.parse(args)
    }
}