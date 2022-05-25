import java.io.File

class DeepWalker {

    fun isExist(path: String): Boolean {
        return File(path).exists()
    }

    fun readFile(filePath: String) = File(filePath).readLines()

    fun readDirectory(info: Info): MutableMap<String, List<String>> {
        val lines = mutableMapOf<String, List<String>>()
        try {
            File(info.filePath).walk().forEach {
                if (it.isFile && !info.listExclude.contains(it.toString().substringAfterLast("\\"))) {
                    val resultText: MutableList<String> = mutableListOf()
                    resultText.addAll(it.readLines())
                    lines[it.toString()] = resultText
                }
            }
        } catch (e: java.lang.Exception) {
            println(e.message)
        }
        return lines
    }
}