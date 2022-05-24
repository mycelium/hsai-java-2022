data class GrekCommand (
    var pathToFile: String = "",
    var ignoreCase: Boolean = false,
    var help: Boolean = false,
    var exclude: String = "",
    var regex: String = "",
    var numberLine: Boolean = false,
    var recursiveSearch: Boolean = false,
    var beforeContext: Int = 0,
    var afterContext: Int = 0
)

fun createCommand(args: List<String>): GrekCommand {
    val command = GrekCommand()

    for ((index, value) in args.withIndex()) {
        when (value) {
            "-n" -> command.numberLine = true
            "-r" -> command.recursiveSearch = true
            "-nr" -> {
                command.numberLine = true
                command.recursiveSearch = true
            }
            "-A" -> command.afterContext = args[index + 1].toInt()
            "-B" -> command.beforeContext = args[index + 1].toInt()
            "-i" -> command.ignoreCase = true
            "--help" -> {
                command.help = true
                print("grek [-n][-r] [-A] [-B] [--help]\n" +
                        "-n - print lines\n" +
                        "-r - recursive walk\n" +
                        "-A - lines of trailing context after each find regex\n" +
                        "-B - lines of leading context before each find regex\n" +
                        "--help - print help")
            }
            "--exclude" -> command.exclude = args[args.lastIndex - 2]
        }
    }
    command.regex = args[args.lastIndex - 1]
    command.pathToFile = args[args.lastIndex]
    return command
}

