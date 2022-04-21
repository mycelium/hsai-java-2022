import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Stream

class FileWalker(private val path: Path, private val filter: FileFilter) {
    fun walk(): Stream<Path> {
        return Files.walk(path).filter(filter)
    }
}