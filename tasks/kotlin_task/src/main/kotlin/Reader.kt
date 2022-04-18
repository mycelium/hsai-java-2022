import java.io.File

class Reader {

    private var fileExists: Boolean = false

    fun checkExists(path: String): Boolean {
        fileExists = File(path).exists()
        return fileExists
    }

    fun readFile(filepath: String) = File(filepath).readLines()

    fun readDirectory(settings: Arguments): MutableMap<String, List<String>> {
        val map = mutableMapOf<String, List<String>>()
        val resultText: MutableList<String> = mutableListOf()
        try {
            File(settings.path).walk().forEach {
                if (it.isFile && !settings.excludeList.contains(it.toString())) {
                    resultText.add(it.readLines().toString())
                    map.put(it.toString(), resultText)
                }
            }
        } catch (e: java.lang.Exception) {
            println(e.message)
        }
        return map
    }
}