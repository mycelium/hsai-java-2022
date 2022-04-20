import java.lang.Integer.min
import java.lang.Integer.max
import java.nio.file.Paths
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.collections.MutableMap

//Пример запроса -n " епископ " data/test.txt -A 3 -B 2
//В test.txt между двумя строками есть пустая строка!

//Пример запроса -nr " [а-я]{12} " data --exclude "data/test.txt" -i

//Пример запроса -nr " [а-я]{12} " data --exclude "data/test.txt|data/test2.txt"
//Ничего не найдется

//Пример запроса --help

fun find(line_arr: List<String>, A: Int, B: Int, regex: Regex): List<String> {
    var result = listOf<String>()
    var str_count=0
    for (i in line_arr.indices) {
        str_count++
        if (line_arr[i].contains(regex)){
            if (B>0)
                for (j in max(0, i-B) until i)
                    result = result + line_arr[j]
            result = result + ("*FIND*: str${str_count} : +${line_arr[i]}")
            if (A>0)
                for (k in i+1..min(line_arr.size-1, i+A))
                    result = result + line_arr[k]
            result = result + "----------------------------------"
        }
    }
    return result
}
fun map_args(args: Array<String>): MutableMap<String, String> {
    var dict_arr = mutableMapOf<String, String>()
    var i = 0
    while(i<args.size){
       when (args[i]) {
            "--help" -> {
                dict_arr["--help"] = "1"
                i++
            }
            "-i" -> {
                dict_arr["-i"] = "1"
                i++
            }
            "-n" -> {
                dict_arr["-n"] = args[i + 2]
                dict_arr["regexN"] = args[i + 1]
                i += 3
            }
            "-nr" -> {
                dict_arr["-nr"] = args[i + 2]
                dict_arr["regexNR"] = args[i + 1]
                i += 3
            }
            "-A" -> {
                dict_arr["-A"] = args[i + 1]
                i += 2
            }
            "-B" -> {
                dict_arr["-B"] = args[i + 1]
                i += 2
            }
            "--exclude" -> {
                dict_arr["--exclude"] = args[i + 1]
                i += 2
            }
            else -> {
                dict_arr = mutableMapOf("Error" to "-1")
                break
            }
        }
    }
    return dict_arr
}
fun main(args: Array<String>){
    val dict = map_args(args)

    if(dict.contains("Error")|| dict.isEmpty()){
        println("Unknown key or no key")
        return
    }

    if(dict.contains("-n"))
        if (!Files.exists(Paths.get(dict["-n"]!!))) {
            println("File or directory not exist")
            return
        }

    if(dict.contains("-nr"))
        if (!Files.exists(Paths.get(dict["-nr"]!!))) {
            println("File or directory not exist")
            return
        }

    var excl= listOf<Path>()
    if(dict.contains("--exclude")) {
        excl = dict["--exclude"]!!.split("|").stream().map(Paths::get).collect(Collectors.toList())
        for (e in excl)
            if (!Files.exists(e)) {
                println("File or directory not exist")
                return
            }
    }

    var A = 0
    var B = 0
    if(dict.contains("-A"))
        A=dict["-A"]!!.toInt()
    if(dict.contains("-B"))
        B=dict["-B"]!!.toInt()

    if (dict.contains("--help"))
        println(
            "Grek utility\n-n <regEx> <pathToSingleFile>: Find in file" +
                    "\n-nr <regEx> <pathToFolder> : Find in files in folder" +
                    "\n-A <Int> : Strings after found" +
                    "\n-B <Int> : Strings before found" +
                    "\n--exclude \"<pathToSingleFile>[|<pathToSingleFile>[|<pathToSingleFile>...]]\" : Exclude file(s) into -nr " +
                    "\n--help : Help (You are here)" +
                    "\n-i : Ignore the case" +
                    "\n========================="
        )

    if(dict.contains("-n")){
        println("Found in file: ${dict["-n"]} on request: ${dict["regexN"]}")
        val regex : Regex = if(dict.contains("-i"))
            dict["regexN"]!!.toRegex(RegexOption.IGNORE_CASE)
        else
            dict["regexN"]!!.toRegex()
        val result = find(Files.readAllLines(Paths.get(dict["-n"]!!)), A, B, regex)
        if (result.isNotEmpty()) println(result.joinToString("\n")+"\n===========================")
    }
    if(dict.contains("-nr")){
        println("Found in location: ${dict["-nr"]} on request: ${dict["regexNR"]}")
        val paths = Files.walk(Paths.get(dict["-nr"]!!)).filter(Files::isRegularFile).toList().minus(excl.toSet())
        val regex : Regex = if(dict.contains("-i"))
            dict["regexNR"]!!.toRegex(RegexOption.IGNORE_CASE)
        else
            dict["regexNR"]!!.toRegex()
        for (p in paths) {
            val result = find(Files.readAllLines(p), A, B, regex)
            if (result.isNotEmpty()) println("Found in file: $p \n${result.joinToString("\n")} ")
        }
    }

}