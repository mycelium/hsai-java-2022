import java.io.File
import kotlin.system.exitProcess

object Process {
    private var fileExists: Boolean = false

    private fun fileExists(path: String): Boolean {
        fileExists = File(path).exists()
        return fileExists
    }

    fun initialize(args: Array<String>, flags: Flags, values: Values) {
        for (arg in args) {
            if (arg.contains("--help")) {
                printHelp()
                exitProcess(1)
            }
            if (arg.contains("-i")) {
                flags.ignoreRegister = true
                continue
            }
            if (arg.contains("-nr")) {
                flags.catalogCheck = true
                continue
            }
            if (arg.contains("-n")) {
                flags.fileCheck = true
                continue
            }
            if (arg.contains("-B")) {
                values.beforeContext = arg.substring(arg.indexOf('B') + 1).toInt()
                continue
            }
            if (arg.contains("-A")) {
                values.afterContext = arg.substring(arg.indexOf('A') + 1).toInt()
                continue
            }
            if (arg.contains("--exclude")) {
                val exclusiveFiles = arg.substring(arg.indexOf('=') + 1).split("|")
                values.exclude.addAll(exclusiveFiles)
                continue
            }
        }
        values.pattern = args[args.size - 2]
        values.path = args[args.size - 1]
    }

    fun fileProcessor(flags: Flags, values: Values) {
        if (fileExists(values.path)) {
            val lines = Reader.readFile(values.path)
            findRegLines(flags, values, lines, values.path)
        } else {
            println("File path not found!")
            exitProcess(-1)
        }
    }

    fun catalogProcessor(flags: Flags, values: Values) {
        if (fileExists(values.path)) {
            val filesLines = Reader.readCatalog(values)
            for (fl in filesLines)
                findRegLines(flags, values, fl.value, fl.key)
        } else {
            println("File path not found!")
            exitProcess(-1)
        }
    }

    private fun findRegLines(flags: Flags, values: Values, strings: List<String>, fileName: String) {
        val regex = if (flags.ignoreRegister) values.pattern.toRegex(RegexOption.IGNORE_CASE)
        else values.pattern.toRegex()

        for (i in strings.indices) {
            if (regex.containsMatchIn(strings[i])) {
                println("Found in $fileName")
                val greenPrimeColor = "\u001b[32m"
                val redBeforeColor = "\u001b[31m"
                val blueAfterColor = "\u001b[34m"
                val reset = "\u001b[0m"
                if (values.beforeContext != 0) {
                    for (j in Integer.max(0, i - values.beforeContext) until i) {
                        println(redBeforeColor + strings[j] + reset)
                    }
                }
                println(greenPrimeColor + strings[i] + reset)
                if (values.afterContext != 0) {
                    for (j in i + 1..Integer.min(strings.size - 1, i + values.afterContext)) {
                        println(blueAfterColor + strings[j] + reset)
                    }
                }
            }
        }
    }

    private fun printHelp() {
        println("Grek utility")
        println("Pattern: Key(s) <RegExp> <PathToSingleFile>/<PathToCatalog>")
        println("Keys: -n - Single file\n-nr - Catalog\n-A<Int> - strings after current one\n-B<Int> - strings before current one" +
                "\n-i - Ignore case\n--exclude - excluded files in catalog search\n--help - get help")
        println("Example: -nr -i -A1 -B1 --exclude=test1|test3 [a-z]{5} Samples")
    }
}

