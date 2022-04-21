import java.io.File

data class GrekParams(
        var regex: Regex = Regex(""),
        var path: String = "",
        var printLines: Boolean = false,
        var recursive: Boolean = false,
        var after: Int = 0,
        var before: Int = 0,
        var help: Boolean = false
)

class Grek(private val params: GrekParams) {

    fun run(): String {
        if (params.help) {
            return ""
        }
        val file = File(params.path)
        if (!file.exists() || !file.isDirectory && !file.isFile) {
            throw IllegalArgumentException("${params.path} does not exist")
        }
        return findRecursiveDirectory(file, params.regex, params.printLines, params.recursive)
    }

    private fun findRecursiveDirectory(
            directory: File,
            regex: Regex,
            printLines: Boolean,
            recursive: Boolean
    ): String {
        var res =""
        directory.listFiles().forEach {
            if (it.isDirectory && recursive) {
                res += findRecursiveDirectory(it, regex, printLines, recursive)
            }
            if (it.isFile) {
                val resFromFile = findRecursive(it, regex, printLines)
                if (!resFromFile.isEmpty()) {
                    res += "${it}\n${resFromFile}"
                }
            }
        }
        return res
    }

    private fun findRecursive( file: File, regex: Regex, printLines: Boolean): String {
        val lines = file.readLines()
        var res = ""
        for(i in lines.indices) {
            regex.find(lines[i])?.let {
                for (j in maxOf(i - params.before, 0)..minOf(i - params.after, lines.size - 1)) {
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