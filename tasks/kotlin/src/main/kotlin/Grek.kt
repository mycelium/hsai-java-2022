import java.io.File

data class GrekParams(
        var regex: Regex = Regex(""),
        var path: String = "",
        var printLines: Boolean = false,
        var recursive: Boolean = false,
        var afterContext: Int = 0,
        var beforeContext: Int = 0
)

class Grek {
    fun run(params: GrekParams): String {
        val file = File(params.path)
        if (!file.exists() || !file.isDirectory && !file.isFile) {
            throw IllegalArgumentException("${params.path} does not exist")
        }
        val context = Pair(params.beforeContext, params.afterContext)
        return if (file.isDirectory) {
            findRecursiveDirectory(file, params.regex, params.printLines, context, params.recursive)
        } else {
            findRecursiveFile(file, params.regex, params.printLines, context)
        }
    }

    private fun findRecursiveDirectory(
            directory: File,
            regex: Regex,
            printLines: Boolean,
            context: Pair<Int, Int>,
            recursive: Boolean
    ): String {
        val files = directory.listFiles()
        var res = ""
        if (files.isEmpty()) {
            return res
        }
        for (file in files) {
            if (file.isDirectory && recursive) {
                res += findRecursiveDirectory(file, regex, printLines, context, recursive)
            }
            if (file.isFile) {
                val resFromFile = findRecursiveFile(file, regex, printLines, context)
                if (!resFromFile.isEmpty()) {
                    res += "${file}\n${resFromFile}"
                }
            }
        }
        return res
    }

    private fun findRecursiveFile(file: File, regex: Regex, printLines: Boolean, context: Pair<Int, Int>): String {
        val (beforeBound, afterBound) = context
        val lines = file.readLines()
        var res = ""
        for(lineIndex in lines.indices) {
            regex.find(lines[lineIndex])?.let {
                val minBound = minOf(lineIndex + afterBound, lines.size - 1)
                val maxBound = maxOf(lineIndex - beforeBound, 0)
                val contextRange = maxBound..minBound
                for (contextLineIndex in contextRange) {
                    if (printLines) {
                        res += "${contextLineIndex + 1}: "
                    }
                    res += lines[contextLineIndex]
                }
            res += "\n"
            }
        }
        return res
    }
}