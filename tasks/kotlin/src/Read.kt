import java.io.File

object Reader {

    fun readFile(filepath: String) = File(filepath).readLines()

    fun readCatalog(values: Values): MutableMap<String, List<String>> {
        val map = mutableMapOf<String, List<String>>()
        try {
            File(values.path).walk().forEach {
                if (it.isFile && !values.exclude.contains(it.toString().substringAfterLast("\\"))) {
                    val resultText: MutableList<String> = mutableListOf()
                    resultText.addAll(it.readLines())
                    map[it.toString()] = resultText
                }
            }
        } catch (e: java.lang.Exception) {
            println(e.message)
        }
        return map
    }
}