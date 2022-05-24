import java.io.File

class CommandLine(private val param: GrekCommand) {

    fun run():String{
        if (param.help){
            return ""
        }
        val file = File(param.pathToFile)
        if (!file.exists() || !file.isDirectory && !file.isFile) {
            throw IllegalArgumentException("${param.pathToFile} does not exist")
        }
        return findRecursiveDir(file, param.regex, param.numberLine, param.recursiveSearch, param.ignoreCase, param.beforeContext, param.afterContext)
    }

    private fun findRecursiveDir(
        directory: File,
        regex: String,
        printLines: Boolean,
        recursive: Boolean,
        ignoreCase: Boolean,
        beforeContext: Int,
        afterContext: Int
    ): String {
        var res =""
        val context = Pair(beforeContext, afterContext)
        directory.listFiles().forEach {
            if (it.isDirectory && recursive) {
                res += findRecursiveDir(it, regex, printLines, recursive, ignoreCase, beforeContext, afterContext)
            }
            if (it.isFile) {
                val resFromFile = find(it, regex, printLines, ignoreCase, context)
                if (!resFromFile.isEmpty()) {
                    res += "${it}\n${resFromFile}"
                }
            }
        }
        return res
    }

    private fun find(
        file: File,
        regex: String,
        printLines: Boolean,
        ignoreCase: Boolean,
        context: Pair <Int, Int>,
    ): String {
        val rgx: String
        val (before, after) = context
        val lines = file.readLines()
        if(ignoreCase){
            rgx = regex.lowercase()
        }
        else{
            rgx = regex
        }
        var res = ""
        for(i in lines.indices) {
            rgx.toRegex().find(lines[i])?.let {
                for (j in maxOf(i - before, 0)..minOf(i + after, lines.size - 1)) {
                    if (printLines) {
                        res += "${j + 1}: "
                    }
                    res += "${lines[j]}\n"

                }
            }
        }
        return res
    }

}