import java.io.File
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.absolute
import kotlin.math.max
import kotlin.math.min

class Searcher(argumentSet: ArgumentSet) {
    val args: ArgumentSet = argumentSet

    fun readLines(path: Path): List<String> {
        val lines: MutableList<String> = ArrayList()
        return Files.readAllLines(path)
    }

    fun searchRecursive(): List<String> {
        val path = Paths.get(args.path)

        if (!args.recursive && Files.isDirectory(path)) {
            throw IllegalArgumentException("grek: " + path.fileName.toString() + ": Is a directory")
        }

        val exclude = Regex(args.exclude)

        val pathMatches = Files.walk(path)
            .filter( { p -> (args.exclude == "" || !exclude.containsMatchIn(p.toString())) && Files.isRegularFile(p) })
            .map({ p -> p.relativize(path.toAbsolutePath().parent) })
            .toList()

        if (pathMatches.size == 1) {
            return search(pathMatches[0])
        }

        val res: MutableList<String> = ArrayList()
        for (p: Path in pathMatches) {
            res.addAll(search(p).map({ str -> p.toString() + ":" + str }))
        }

        return res
    }

    fun search(path: Path): List<String> {
        return search(readLines(path))
    }

    fun search(lines: List<String>): List<String> {
        val res: MutableList<String> = ArrayList()
        var lastAdded: Int = -1

        val regex: Regex = if (args.ignoreCase) Regex(args.pattern, RegexOption.IGNORE_CASE) else Regex(args.pattern)

        for (match: Int in 0..lines.size-1) {
            if (regex.containsMatchIn(lines.get(match))) {
                for (i: Int in max(lastAdded+1,match-args.before) .. min(lines.size-1, match+args.after)) {
                    if (args.lineNum) {
                        res.add((i+1).toString() + ":" + lines[i])
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