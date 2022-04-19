import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File
import java.lang.IllegalArgumentException
import java.nio.file.Paths
import kotlin.streams.toList

class CommandsHandler {
    constructor(parser: ArgParser) {
        this.fileRoot = File(fileString)
        this.S = File.separator
    }

    private val n by parser.flagging("-n", help = "флаг номера строки")
    private val r by parser.flagging("-r", help = "флаг рекурсии")
    private val A by parser.storing("-A",  help = "число строк после найденного")
        { toInt() }.default(0)
    private val B by parser.storing("-B",  help = "число строк перед найденным")
        { toInt() }.default(0)
    private val regex by parser.positional("REGEX", help = "регулярка")
        { Regex(this) }
    private val fileString by parser.positional( "FILE", help = "файл/директория для поиска")
        .default(Paths.get("").toAbsolutePath().toString())

    private val fileRoot
    private val S

    fun getContext(file: File): Context =
        Context(file, regex, n, fileRoot.isDirectory, A, B, "$fileString${S}${file.toRelativeString(fileRoot)}")

    fun hand(): List<List<String>> {
        if (!r && fileRoot.isDirectory) {
            throw IllegalArgumentException("Это директория")
        }
        return getFiles(fileRoot)
            .parallelStream()
            .map { FileStringsHandler(getContext(it)).run() }
            .toList()
            .filterNot { it.isEmpty() }
    }
}

fun getFiles(file : File): List<File> = file
    .walk()
    .toList()
    .filter { x -> x.isFile }