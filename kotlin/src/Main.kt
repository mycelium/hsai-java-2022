import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

//Example
//-n "Сильные" resources/text1.txt -A 1
//Found in str1: Сильные умы никогда не бывают послушными., Джек Лондон.

fun main(args: Array<String>) {  //аргументы с консоли
    val dict = grekArgs(args)
    var excl = listOf<Path>()
    var a = 0
    var b = 0

    if (dict.contains("-n"))
        if (!Files.exists(Paths.get(dict["-n"]!!))) {
            println("File or directory doesn't exist")
            return
        }

    if (dict.contains("-nr"))
        if (!Files.exists(Paths.get(dict["-nr"]!!))) {
            println("File or directory doesn't exist")
            return
        }

    if (dict.contains("--exclude")) {
        excl = dict["--exclude"]!!.split("|").stream().map(Paths::get).collect(Collectors.toList())
        for (e in excl)
            if (!Files.exists(e)) {
                println("File or directory doesn't exist")
                return
            }
    }
    if (dict.contains("Error") || dict.isEmpty()) {
        println("Unknown key / empty key")
        return
    }

    if (dict.contains("-A"))
        a = dict["-A"]!!.toInt()

    if (dict.contains("-B"))
        b = dict["-B"]!!.toInt()

    if (dict.contains("--help")) {
        val res = "Grek console utility\n-n <regEx> <pathToSingleFile> Find in file" +
                "\n-nr <regEx> <pathToFolder> -> find in files in folder" +
                "\n-A n <Int>                 -> n strings after" +
                "\n-B n <Int>                 -> n strings before" +
                "\n--exclude \"<pathToSingleFile>[|<pathToSingleFile>[|<pathToSingleFile>...]]\" -> Exclude file(s) into -nr " +
                "\n--help                     -> Help (You are here)" +
                "\n-i                         ->  Ignore the case" +
                "\n                                            \n"
        println(res)
    }

    if (dict.contains("-n")) {
        println("Found in file: ${dict["-n"]} on: ${dict["regexN"]}")
        val regex: Regex = if (dict.contains("-i"))
            dict["regexN"]!!.toRegex(RegexOption.IGNORE_CASE)
        else
            dict["regexN"]!!.toRegex()
        val result = grekFind(Files.readAllLines(Paths.get(dict["-n"]!!)), a, b, regex)
        if (result.isNotEmpty()) println(result.joinToString("\n") + "\n____________________")
    }

    if (dict.contains("-nr")) {
        println("Found in path: ${dict["-nr"]} on: ${dict["regexNR"]}")
        val paths = Files.walk(Paths.get(dict["-nr"]!!)).filter(Files::isRegularFile).toList().minus(excl.toSet())
        val regex: Regex = if (dict.contains("-i"))
            dict["regexNR"]!!.toRegex(RegexOption.IGNORE_CASE)
        else
            dict["regexNR"]!!.toRegex()
        for (p in paths) {
            val result = grekFind(Files.readAllLines(p), a, b, regex)
            if (result.isNotEmpty()) println("Found in file: $p \n${result.joinToString("\n")} ")
        }
    }
}