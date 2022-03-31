package kt_labs.src.main.grek

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File
import java.lang.IllegalArgumentException
import java.nio.file.Paths
import kotlin.streams.toList

class CommandsHandler(parser: ArgParser) {
    private val n by parser.flagging("-n", help = "флаг номера строки")
    private val r by parser.flagging("-r", help = "флаг рекурсии")
    private val A by parser.storing("-A",  help = "число строк после найденного")
    { toInt() }.default(0)
    private val B by parser.storing("-B",  help = "число строк перед найденным")
    { toInt() }.default(0)
    private val regex by parser.positional("REGES", help = "регулярка, по которой нужно искать")
    { Regex(this) }
    private val fileString by parser.positional( "FILE", help = "файл/директория для поиска")
        .default(Paths.get("").toAbsolutePath().toString())

    private val fileRoot = File(fileString)
    private val S = File.separator

    fun getContext(file: File): Context {
        return Context(file, regex, n, fileRoot.isDirectory, A, B, "$fileString${S}${file.toRelativeString(fileRoot)}")
    }

    fun hand(): List<List<String>> {
        if (!r && fileRoot.isDirectory) {
            throw IllegalArgumentException("This is directory")
        }
        return getFiles(fileRoot)
            .parallelStream()
            .map { FileStringsHandler(getContext(it)).run() }
            .toList()
            .filterNot { it.isEmpty() }
    }
}

fun getFiles(file : File): List<File> {
    return file
        .walk()
        .toList()
        .filter { x -> x.isFile }
}