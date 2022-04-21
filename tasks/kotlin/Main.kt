fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw Exception("No arguments found")
    }
    val args: Arguments
    try {
        args = createArguments(args)
    } catch (e: Throwable) {
        print ("Arguments creation failed. ${e.message}")
        return
    }

    if (args.help) {
        Commands.helpList()
    }

    val file = File(args.path)
    if (!args.help && (!file.exists() || !file.isDirectory && !file.isFile)) {
        throw IllegalArgumentException("Current path does not exist")
    }

    val context = Pair(args.previousRegex, args.subsequentRegex)
    return if (file.isDirectory) {
        Commands.checkDirectory(file, args.regex, args.lineNumber, context, args.recursive, args.exclude, args.ignoreCase)
    }
    else {
        Commands.checkFile(file, args.regex, args.lineNumber, context, args.exclude, args.ignoreCase)
    }
}