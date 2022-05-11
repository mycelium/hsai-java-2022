import java.lang.Integer.min
import java.lang.Integer.max
import kotlin.collections.MutableMap

fun grekFind(line_arr: List<String>, A: Int, B: Int, regex: Regex): List<String> {
    var res = listOf<String>()
    var count = 0
    for (i in line_arr.indices) {
        count++
        if (line_arr[i].contains(regex)) {
            if (B > 0)
                for (j in max(0, i - B) until i)
                    res = res + line_arr[j]
            res = res + ("Found in str${count}: ${line_arr[i]}")
            if (A > 0)
                for (k in i + 1..min(line_arr.size - 1, i + A))
                    res = res + line_arr[k]
        }
    }
    return res
}

fun grekArgs(args: Array<String>): MutableMap<String, String> {
    var map = mutableMapOf<String, String>()
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            //доп ключи
            "-i" -> {
                map["-i"] = "1"
                i++
            }
            "--help" -> {
                map["--help"] = "1"
                i++
            }
            "--exclude" -> {
                map["--exclude"] = args[i + 1]
                i += 2
            }
            //grek
            "-n" -> {
                map["-n"] = args[i + 2]
                map["regexN"] = args[i + 1]
                i += 3
            }
            "-nr" -> {
                map["-nr"] = args[i + 2]
                map["regexNR"] = args[i + 1]
                i += 3
            }
            "-A" -> {
                map["-A"] = args[i + 1]
                i += 2
            }
            "-B" -> {
                map["-B"] = args[i + 1]
                i += 2
            }
            else -> {
                map = mutableMapOf("Error" to "-1")
                break
            }
        }
    }
    return map
}




