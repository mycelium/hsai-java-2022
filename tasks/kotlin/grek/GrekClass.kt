import java.io.File

class GrekClass (private val params: Parameters) {

    fun execute(): String = with(File(params.filename)) {
        if (!exists() || !isDirectory && !isFile)
            throw IllegalArgumentException("$path doesn't exist")
        val ctx = Pair(params.beforeCtx, params.afterCtx)
        return if (isDirectory)
            directorySearch(this, params.regex, ctx, params.shouldLinesBePrinted, params.isRecursive)
        else
            fileSearch(this, params.regex, ctx, params.shouldLinesBePrinted)
    }

    private fun directorySearch(dir: File, regex: Regex, ctx: Pair<Int, Int>, shouldLinesBePrinted: Boolean, isRecursive: Boolean): String {
        var results = ""
        dir.listFiles()?.let { files ->
            if (files.isEmpty()) return results
            for (file in files) {
                if (file.isDirectory && isRecursive)
                    results += directorySearch(file, regex, ctx, shouldLinesBePrinted, true)
                if (file.isFile) {
                    val fileResults = fileSearch(file, regex, ctx, shouldLinesBePrinted)
                    if (fileResults.isNotEmpty()) results += "${file}\n${fileResults}"
                }
            }
        }
        return results
    }

    private fun fileSearch(file: File, regex: Regex, ctx: Pair<Int, Int>, shouldLinesBePrinted: Boolean): String {
        val (beforeBound, afterBound) = ctx
        val lines = file.readLines()
        var results = ""
        for (lineIndex in lines.indices) {
            regex.find(lines[lineIndex])?.let {
                val minBound = minOf(lineIndex + afterBound, lines.size - 1)
                val maxBound = maxOf(lineIndex - beforeBound, 0)
                val ctxRange = maxBound..minBound
                for (ctxLineIndex in ctxRange) {
                    if (shouldLinesBePrinted) results += "${ctxLineIndex + 1}: "
                    results += lines[ctxLineIndex]
                }
                results += "\n"
            }
        }
        return results
    }

}
