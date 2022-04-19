import java.io.File
import java.lang.IllegalArgumentException
import java.nio.charset.Charset

class FileStringsHandler(private val contxt: Context) {
    private val listOfLinesFromFile: List<String> = getLines(contxt.file)

    private fun getLines(file: File): List<String> = file
        .readLines(Charset.defaultCharset())

    private fun findIndexes(listOfLines: List<String>, regex: Regex, A: Int, B: Int): List<Int> {
        val filtred = listOfLines
            .mapIndexed { index, s -> Pair(index, s) }
            .filter { s -> regex.containsMatchIn(s.second) }
            .map { pair -> pair.first + 1 }

        val res = mutableListOf<Int>()
        for (i in filtred) for (j in i-B..i+A) if (j >= 1 && j <= listOfLines.size) res.add(j)
        return res.distinct()
    }

    private fun filter(listOfLines: List<String>, listOfIndexes: List<Int>): List<String> = listOfLines
        .filterIndexed { index, _ -> listOfIndexes.contains(index + 1) }

    private fun concatNumber(listOfLines: List<String>, listOfIndexes: List<Int>): List<String> {
        if (listOfLines.size != listOfIndexes.size) throw IllegalArgumentException(
            "Size of (listOfLines=${listOfLines.size}) != (listOfIndexes=${listOfIndexes.size})")
        return listOfLines
            .mapIndexed { index, s -> "${listOfIndexes[index]}:${s}" }
    }

    private fun concatName(listOfLines: List<String>, fileName: String): List<String> = listOfLines
        .map { str -> "${fileName}:${str}" }

    fun run(): List<String> {
        val listOfIndexes : List<Int> =
            findIndexes(this.listOfLinesFromFile, contxt.regex, contxt.A, contxt.B)
        var listResult: List<String> = filter(this.listOfLinesFromFile, listOfIndexes)

        if (contxt.n) listResult = concatNumber(listResult, listOfIndexes)
        if (contxt.r) listResult = concatName(listResult, contxt.relativeFileName)
        return listResult
    }
}