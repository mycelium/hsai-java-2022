import kotlin.system.exitProcess

class Handler(deepWalker: DeepWalker) {
    fun processFile(booleans: Booleans, info: Info, deepWalker: DeepWalker) {
        if (deepWalker.isExist(info.filePath)) {
            val strings = deepWalker.readFile(info.filePath)
            matchPatternStrings(booleans, info, strings, info.filePath)
        } else {
            println("File path is wrong!")
            exitProcess(-1)
        }
    }

    fun processDirectory(booleans: Booleans, info: Info, deepWalker: DeepWalker) {
        if (deepWalker.isExist(info.filePath)) {
            val filesStrings = deepWalker.readDirectory(info)
            for (current in filesStrings)
                matchPatternStrings(booleans, info, current.value, current.key)
        } else {
            println("File path is wrong!")
            exitProcess(-1)
        }
    }

    private fun matchPatternStrings(booleans: Booleans, info: Info, strings: List<String>, fileName: String) {
        val rgx = if (booleans.ignoreRegister) info.pattern.toRegex(RegexOption.IGNORE_CASE)
        else info.pattern.toRegex()

        for (i in strings.indices) {
            if (rgx.containsMatchIn(strings[i])) {
                println("Find in file -> $fileName")
                if (info.beforeRows != 0) {
                    for (j in Integer.max(0, i - info.beforeRows) until i) {
                        println(strings[j])
                    }
                }
                println(strings[i])
                if (info.afterRows != 0) {
                    for (j in i + 1..Integer.min(strings.size - 1, i + info.afterRows)) {
                        println(strings[j])
                    }
                }
                println("")
            }
        }
    }

    fun assign(args: Array<String>, booleans: Booleans, info: Info) {
        for (arg in args) {
            with(arg) {
                when {
                    contains("--help") -> {
                        showHelp()
                        exitProcess(1)
                    }
                    contains("-i") -> booleans.ignoreRegister = true
                    contains("--exclude") -> {
                        val files = substring(indexOf('=') + 1).split("|")
                        info.listExclude.addAll(files)
                    }
                    contains("-nr") -> booleans.checkDirectory = true
                    contains("-n") -> booleans.checkFile = true
                    contains("-B") -> info.beforeRows = substring(indexOf('B') + 1).toInt()
                    contains("-A") -> info.afterRows = substring(indexOf('A') + 1).toInt()
                    else -> { }
                }
            }
        }
        info.pattern = args[args.size - 2]
        info.filePath = args[args.size - 1]
    }

    private fun showHelp() {
        println("Grek Utility")
        println("Keys: \n-n -> find in file\n-nr -> find in files in directory")
        println("-An -> count of rows after found (n - number of rows)\n-Bn -> count of rows before found (n - number of rows)")
        println("--exclude -> exclude file(s) in -nr\n-i -> ignore the case\n--help -> get help")
        println("*Last two elements must be regex pattern and path*")
        println("Example: -nr -i -A2 -B3 --exclude=file1|file2 (test1|test2) pattern (we/ [a-z]{4} / [0-9]) directory (data)")
    }
}