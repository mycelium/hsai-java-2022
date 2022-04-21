import java.io.File

class Grek(private val parameters: GrekParams) {

    fun run(): String = with(File(parameters.path)) {
        if (!exists() || !isDirectory && !isFile) throw IllegalArgumentException("$path does not exist")
        val context = Pair(parameters.beforeContext, parameters.afterContext)
        return if (isDirectory) {
            recursiveSearchDirectory(
                this,
                parameters.regex,
                parameters.printLines,
                context,
                parameters.recursive
            )
        } else {
            recursiveSearchFile(
                this,
                parameters.regex,
                parameters.printLines,
                context
            )
        }
    }

    private fun recursiveSearchDirectory(
        directory: File,
        regex: Regex,
        printLines: Boolean,
        context: Pair<Int, Int>,
        recursive: Boolean
    ): String {
        var res = ""
        directory.listFiles()?.let { files ->
            if (files.isEmpty()) return res
            for (file in files) {
                if (file.isDirectory && recursive) {
                    res += recursiveSearchDirectory(file, regex, printLines, context, true)
                }
                if (file.isFile) {
                    val resFromFile = recursiveSearchFile(file, regex, printLines, context)
                    if (resFromFile.isNotEmpty()) res += "${file}\n${resFromFile}"
                }
            }
        }
        return res
    }

    private fun recursiveSearchFile(
        file: File,
        regex: Regex,
        printLines: Boolean,
        context: Pair<Int, Int>
    ): String {
        val (beforeBound, afterBound) = context
        val lines = file.readLines()
        var res = ""
        for (lineIndex in lines.indices) {
            regex.find(lines[lineIndex])?.let {
                val minBound = minOf(lineIndex + afterBound, lines.size - 1)
                val maxBound = maxOf(lineIndex - beforeBound, 0)
                val contextRange = maxBound..minBound
                for (contextLineIndex in contextRange) {
                    if (printLines) res += "${contextLineIndex + 1}: "
                    res += lines[contextLineIndex]
                }
                res += "\n"
            }
        }
        return res
    }

}
