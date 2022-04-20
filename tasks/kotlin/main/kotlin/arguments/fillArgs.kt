package arguments

fun fillArgs(args: Array<String>): Args {
    val arguments = Args()

    for ((pos, value) in args.withIndex()) {
        when (value) {
            "-n" -> arguments.line_number = true
            "-nr" -> { arguments.line_number = true
                arguments.recursive = true }
            "-A" -> arguments.after_context = args[pos + 1].toInt()
            "-B" -> arguments.before_context = args[pos + 1].toInt()
            "-i" -> arguments.ignore_case = true
            "--help" -> arguments.help = true
            "--exclude" -> arguments.exclude = args[args.lastIndex - 2]
        }
    }

    arguments.regex = args[args.lastIndex - 1]
    arguments.path = args[args.lastIndex]

    return arguments
}
