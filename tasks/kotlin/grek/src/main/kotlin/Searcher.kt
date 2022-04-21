import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.math.max
import kotlin.math.min

class Searcher(argumentSet: ArgumentSet) {
    private val args: ArgumentSet = argumentSet

    private fun readLines(path: Path): List<String> {
        return Files.readAllLines(path)
    }

    fun searchRecursive(): List<String> {
        val path = Paths.get(args.path)

        if (!args.recursive && Files.isDirectory(path)) {
            throw IllegalArgumentException("grek: ${path.fileName}: Is a directory")
        }

        val exclude = Regex(args.exclude)

        val pathMatches = Files.walk(path)
            .filter{p -> (args.exclude == "" || !exclude.containsMatchIn(p.toString())) && Files.isRegularFile(p)}
            .map{p -> path.toAbsolutePath().parent.relativize(p.toAbsolutePath())}
            .toList()

        if (pathMatches.size == 1) {
            return search(pathMatches[0])
        }

        val res: MutableList<String> = mutableListOf()
        for (p: Path in pathMatches) {
            res.addAll(search(p).map{str -> "${p}:$str"})
        }

        return res
    }

    fun search(path: Path): List<String> {
        return search(readLines(path))
    }

    fun search(lines: List<String>): List<String> {
        val res: MutableList<String> = mutableListOf()
        var lastAdded: Int = -1

        val regex: Regex = if (args.ignoreCase) Regex(args.pattern, RegexOption.IGNORE_CASE) else Regex(args.pattern)

        for (match: Int in 0 until lines.size) {
            if (regex.containsMatchIn(lines.get(match))) {
                for (i: Int in max(lastAdded+1,match-args.before) .. min(lines.size-1, match+args.after)) {
                    if (args.lineNum) {
                        res.add("${(i+1)}:${lines[i]}")
                    } else {
                        res.add(lines[i])
                    }
                    lastAdded = match+args.after
                }
            }
        }

        return res
    }
}