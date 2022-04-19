import kotlin.system.exitProcess

class Process(reader: Reader) {

    var reader: Boolean = false

    fun FileProcessor(boolset: Bools, valset:Vals, reader: Reader) {
        if (reader.ExistsionCheck(valset.FilePath)) {
            val strings = reader.FileRead(valset.FilePath)
            PatternMatchStrings(boolset, valset, strings, valset.FilePath)
        } else {
            println("File path is wrong!")
            exitProcess(-1)
        }
    }

    fun DirectoryProcessor(boolset: Bools, valset:Vals, reader: Reader) {
        if (reader.ExistsionCheck(valset.FilePath)) {
            val filesStrings = reader.DirectoryRead(valset)
            for (curr in filesStrings)
                PatternMatchStrings(boolset, valset, curr.value, curr.key)
        } else {
            println("File path is wrong!")
            exitProcess(-1)
        }
    }

    private fun PatternMatchStrings(boolset: Bools, valset:Vals, strings: List<String>, fileName: String) {
        val rgx = if (boolset.IgnoreRegister) valset.Pattern.toRegex(RegexOption.IGNORE_CASE)
        else valset.Pattern.toRegex()

        for (i in strings.indices) {
            if (rgx.containsMatchIn(strings[i])) {
                println("Find in file -> $fileName")
                val purple = "\u001b[35m"
                val blue = "\u001b[34m"
                val green = "\u001b[32m"
                val reset = "\u001b[0m"
                if (valset.BeforeRows != 0) {
                    for (j in Integer.max(0, i - valset.BeforeRows) until i) {
                        println(blue + strings[j] + reset)
                    }
                }
                println(purple + strings[i] + reset)
                if (valset.AfterRows != 0) {
                    for (j in i + 1..Integer.min(strings.size - 1, i + valset.AfterRows)) {
                        println(green + strings[j]+ reset)
                    }
                }
                println("")
            }
        }
    }

    fun Assigment(args: Array<String>, boolset: Bools, valset:Vals) {
        for (arg in args) {
            if (arg.contains("--help")) {
                HelpShow()
                exitProcess(1)
            }
            if (arg.contains("-i")) {
                boolset.IgnoreRegister = true
                continue
            }
            if (arg.contains("--exclude")) {
                val files = arg.substring(arg.indexOf('=') + 1).split("|")
                valset.ListExclude.addAll(files)
                continue
            }
            if (arg.contains("-nr")) {
                boolset.CheckDirectory = true
                continue
            }
            if (arg.contains("-n")) {
                boolset.CheckFile = true
                continue
            }
            if (arg.contains("-B")) {
                valset.BeforeRows = arg.substring(arg.indexOf('B') + 1).toInt()
                continue
            }
            if (arg.contains("-A")) {
                valset.AfterRows = arg.substring(arg.indexOf('A') + 1).toInt()
                continue
            }
        }
        valset.Pattern = args[args.size - 2]
        valset.FilePath = args[args.size - 1]
    }

    private fun HelpShow() {
        println("Grek Utility")
        println("Keys: \n-n -> find in file\n-nr -> find in files in directory")
        println("-An -> count of rows after found (n - number of rows)\n-Bn -> count of rows before found (n - number of rows)")
        println("--exclude -> exclude file(s) in -nr\n-i -> ignore the case\n--help -> get help")
        println("*Last two elements must be regex pattern and path*")
        println("Example: -nr -i -A2 -B3 --exclude=file1|file2 (test1|test2) pattern (we/ [a-z]{4} / [0-9]) directory (data)")
    }
}