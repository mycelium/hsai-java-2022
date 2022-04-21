import java.nio.file.Path
import java.util.function.Predicate

class FileFilter(private val excludedFiles: List<Path>): Predicate<Path> {
    override fun test(file: Path): Boolean {
        return !excludedFiles.contains(file)
    }
}