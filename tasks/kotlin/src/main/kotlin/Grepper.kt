import java.nio.file.Files
import java.nio.file.Path

class Grepper(private val lineFilter: LineFilter, private val grepOptions: GrepOptions) {
    class Result(val lines: List<String>, val needsDivide: Boolean)

    fun grep(file: Path): List<Result> {
        val results = mutableListOf<Result>()

        val lines = Files.readAllLines(file)
        val indices = lines.withIndex().filter { a -> lineFilter.test(a.value) }.map { a -> a.index }.toList()
        for (i in indices) {
            val result = mutableListOf<String>()
            val topBarrier = if (i > 0) {
                indices[i - 1] + grepOptions.afterLine
            } else {
                0
            }

            for (back_index in i downTo topBarrier.coerceAtLeast(i - grepOptions.beforeLine)) {
                result.add(lines[back_index])
            }

            for (to_index in topBarrier.coerceAtLeast(i)..indices.size.coerceAtMost(i + grepOptions.afterLine)) {
                result.add(lines[to_index])
            }

            val needsDivide: Boolean =
                (i != indices.size - 1) and
                        grepOptions.needsDivider and !((i < indices.size - 1) and (i + grepOptions.afterLine >= indices[i + 1] - grepOptions.beforeLine))
            results.add(Result(result, needsDivide))
        }

        return results
    }
}