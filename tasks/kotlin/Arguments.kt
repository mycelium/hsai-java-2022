data class Arguments(
    var regex: String = "",
    var path: String = "",
    var exclude: String = "",
    var subsequentRegex: Int = 0,
    var previousRegex: Int = 0,
    var lineNumber: Boolean = false,
    var recursive: Boolean = false,
    var ignoreCase: Boolean = false,
    var help: Boolean = false
)

fun createArguments(args: Array<String>): Arguments {
    val args = Arguments()

    for ((idx, value) in args.withIndex()) {
        when (value) {
            "-n" -> args.lineNumber = true
            "-r" -> args.recursive = true
            "-nr" -> {
                args.lineNumber = true
                args.recursive = true
            }
            "-rn" -> {
                args.lineNumber = true
                args.recursive = true
            }
            "-A" -> args.subsequentRegex = args[idx + 1].toInt()
            "-B" -> args.previousRegex = args[idx + 1].toInt()
            "-i" -> args.ignoreCase = true
            "--help" -> args.help = true
            "--exclude" -> args.exclude = args[args.lastIndex - 2]
        }
    }

    args.regex = args[args.lastIndex - 1]
    args.path = args[args.lastIndex]

    return args
}