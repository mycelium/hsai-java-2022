import java.io.File

data class GrekOptions(
    var regex: Regex = Regex(""),
    var path: String = "",
    var lineNum: Boolean = false,
    var recursive: Boolean = false,
    var beforeContext: Int = 0,
    var afterContext: Int = 0,
    var ignoreCase: Boolean = false,
    var exclude: String = "",
    var help: Boolean = false
)

class Grek(private val options: GrekOptions) {

    fun run(): String {
        if (options.help) {
            return "grek [-n][-r] [-i] [-A NUM] [-B NUM] [--exclude]\n" +
                    "-n - print number of lines\n" +
                    "-r - recursive search\n" +
                    "-i - ignore case\n" +
                    "-A NUM - print NUM lines of trailing context after  matching  line\n" +
                    "-B NUM - print NUM lines of leading context before  matching  line\n" +
                    "--exclude <regex> - skip file if its path matches given regex"
        }
        val file = File(options.path)
        if (!file.exists() || !file.isDirectory && !file.isFile) {
            throw IllegalArgumentException("${options.path} does not exist")
        }
        if (file.isFile()) return findInFile(file)
        else return findInDirectory(file)
    }

    private fun findInDirectory(directory: File): String {
        var res = ""
        directory.listFiles().forEach {
            if (it.isDirectory && options.recursive) {
                res += findInDirectory(it)
            }
            if (it.isFile) {
                val resFromFile = findInFile(it)
                if (!resFromFile.isEmpty()) {
                    res += "${it}\n${resFromFile}"
                }
            }
        }
        return res
    }

    private fun findInFile(file: File): String {
        var res = ""
        if (options.exclude.isNotEmpty() && file.path.contains(Regex(options.exclude, RegexOption.IGNORE_CASE))) {
            return res
        }
        val lines = file.readLines()
        for (i in lines.indices) {
            options.regex.find(lines[i])?.let {
                for (j in maxOf(i - options.beforeContext, 0)..minOf(i + options.afterContext, lines.size - 1)) {
                    if (options.lineNum) {
                        res += "${j + 1}: "
                    }
                    res += "${lines[j]}\n"
                }
            }
        }
        return res
    }
}