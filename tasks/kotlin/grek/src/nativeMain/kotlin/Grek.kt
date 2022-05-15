import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import grek.Engine
import grek.FileReader
import grek.StringRenderer
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.math.max

class Grek : CliktCommand() {
    private val showLines: Boolean by option("-n", help = "Show line numbers").flag()
    private val recursively: Boolean by option("-r", help = "Recursively search subdirectories").flag()
    private val ignoreCase: Boolean by option("-i", help = "Perform case insensitive matching").flag()
    private val excludedFiles: List<String> by option("--exclude", help = "Exclude files from the search").multiple()
    private val linesBefore: Int by option("-B", help = "Show N lines before match").int().default(0)
    private val linesAfter: Int by option("-A", help = "Show N lines after match").int().default(0)
    private val expression: String by argument()
    private val target by argument()

    override fun run() {

        val targetFiles = when {
            FileSystem.SYSTEM.metadata(target.toPath()).isDirectory && recursively -> FileSystem.SYSTEM.listRecursively(
                target.toPath()
            ).filter { path -> FileSystem.SYSTEM.metadata(path).isRegularFile }.toList()
            FileSystem.SYSTEM.metadata(target.toPath()).isRegularFile && !recursively -> listOf(target.toPath())
            else -> listOf()
        }

        val fileReader = FileReader()
        val data = fileReader.readFiles(targetFiles.filterNot { path -> excludedFiles.contains(path.toString()) })

        val engine = Engine(
            if (ignoreCase) expression.toRegex(RegexOption.IGNORE_CASE)
            else expression.toRegex()
        )
        val matchResult = engine.processData(data)

        val stringRenderer = StringRenderer()
        print(stringRenderer.render(matchResult, data, max(linesBefore, 0), max(linesAfter, 0), showLines))
    }
}