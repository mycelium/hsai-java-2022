import java.lang.Exception

fun main(args: Array<String>) {
    val usage = "Usage: \n" +
            "grek [OPTION...] <pathToSingleFile>\n" +
            "grek [OPTION...] -nr <regEx> [OPTION...] <pathToFolder>\n" +
            "grek -B 4 -A 5 -nr <regEx> <pathToFolder>\n" +
            "-B <number> - print lines before matched line\n" +
            "-A <number> - print lines after matched line\n" +
            "-n <regEx> - specifies pattern\n" +
            "-i - ignore case\n" +
            "--help - print usage\n" +
            "--exclude <pathToSingleFile> - exclude file from grekking"

    val argumentsParser = ArgumentsParser(args)

    if (argumentsParser.needsHelp() or args.isEmpty()) {
        println(usage)
        return
    }

    try {
        val lineFilter = LineFilter(argumentsParser.isIgnoreCase(), argumentsParser.getRegexString())
        val grepper = Grepper(lineFilter, argumentsParser.getGrepOptions())
        if (argumentsParser.isRecursive()) {
            val fileFilter = FileFilter(argumentsParser.getExcludedFiles())
            val fileWalker = FileWalker(argumentsParser.getPath(), fileFilter)
            fileWalker.walk().map { p -> grepper.grep(p) }.forEach { l ->
                run {
                    l.forEach { r ->
                        run {
                            for (line in r.lines) {
                                println(line)
                            }
                            if (r.needsDivide) {
                                println("--")
                            }
                        }
                    }
                }
            }
        } else {
            grepper.grep(argumentsParser.getPath()).forEach { r ->
                run {
                    for (line in r.lines) {
                        println(line)
                    }
                    if (r.needsDivide) {
                        println("--")
                    }
                }
            }
        }
    } catch (e: Exception) {
        println("Bad parameters: " + e.message)
        println(usage)
        return
    }
}