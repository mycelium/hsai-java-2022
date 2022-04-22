data class Arguments(
    var line_number: Boolean = false,
    var recursive: Boolean = false,
    var after_context: Int = 0,
    var before_context: Int = 0,
    var ignore_case: Boolean = false,
    var help: Boolean = false,
    var exclude: String = "",
    var regex: String = "",
    var path: String = "",
)

fun fillArgs(args: Array<String>): Arguments {
    val arguments = Arguments()

    for ((key, value) in args.withIndex()) {
        when (value) {
            "-n" -> arguments.line_number = true
            "-nr" -> {
                arguments.line_number = true
                arguments.recursive = true
            }
            "-A" -> arguments.after_context = args[key + 1].toInt()
            "-B" -> arguments.before_context = args[key + 1].toInt()
            "-i" -> arguments.ignore_case = true
            "--help" -> arguments.help = true
            "--exclude" -> arguments.exclude = args[args.lastIndex - 2]
        }
    }

    arguments.regex = args[args.lastIndex - 1]
    arguments.path = args[args.lastIndex]

    return arguments
}
