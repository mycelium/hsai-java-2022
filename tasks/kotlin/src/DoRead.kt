import java.io.File

class Reader {

    private var ExistionFile: Boolean = false

    fun ExistsionCheck(path: String): Boolean {
        ExistionFile = File(path).exists()
        return ExistionFile
    }

    fun FileRead(filepath: String) = File(filepath).readLines()

    fun DirectoryRead(valset: Vals): MutableMap<String, List<String>> {
        val mapstring = mutableMapOf<String, List<String>>()
        try {
            File(valset.FilePath).walk().forEach {
                if (it.isFile && !valset.ListExclude.contains(it.toString().substringAfterLast("\\"))) {
                    val resultText: MutableList<String> = mutableListOf()
                    resultText.addAll(it.readLines())
                    mapstring[it.toString()] = resultText
                }
            }
        } catch (e: java.lang.Exception) {
            println(e.message)
        }
        return mapstring
    }
}