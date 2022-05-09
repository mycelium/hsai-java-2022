class CmdOptions {
    fun getOptions(args: Array<String>): GrekOptions {
        val options = GrekOptions()
        if (args.contains("--help")) {
            options.help = true
        } else if (args.size < 2) {
            throw Exception("Invalid input. Use 'grek --help' to see command information")
        } else {
            for ((index, value) in args.withIndex()) {
                when (value) {
                    "-n" -> options.lineNum = true
                    "-r" -> options.recursive = true
                    "-nr" -> {
                        options.lineNum = true
                        options.recursive = true
                    }
                    "-rn" -> {
                        options.lineNum = true
                        options.recursive = true
                    }
                    "-A" -> options.afterContext = args[index + 1].toInt()
                    "-B" -> options.beforeContext = args[index + 1].toInt()
                    "-i" -> options.ignoreCase = true
                    "--exclude" -> {
                        options.exclude = args[index + 1]
                    }
                }
            }
            if (options.ignoreCase) options.regex = args[args.lastIndex - 1].toRegex(setOf(RegexOption.IGNORE_CASE))
            else options.regex = args[args.lastIndex - 1].toRegex()
            options.path = args[args.lastIndex]
        }
        return options
    }
}