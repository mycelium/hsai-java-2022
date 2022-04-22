import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.readLines

class Handler {

    fun start(args: Array<String>) {
        if (args.isEmpty()) {
            throw IllegalArgumentException("No arguments found")
        }

        if (fillArgs(args).after_context < 0 || fillArgs(args).before_context < 0) {
            throw IllegalArgumentException("Context cannot be negative")
        }

        val element = Paths.get(fillArgs(args).path)
        if (!element.exists() || !element.isDirectory() && !element.isAbsolute) {
            throw IllegalArgumentException("Path was not found")
        }

        val arguments: Arguments = fillArgs(args)

        try {
            println(Handler().launch(arguments))
        } catch (e: Throwable) {
            println("Error command. Enter one of the following commands.\n" + helpFun())
        }
    }

    private fun helpFun(): String {
        return ("\n Help info:\n" +
                " -n: Line number\n" +
                " -nr: Process of all files in directory recursively\n" +
                " -B: Print num lines of leading context before matching lines\n" +
                " -A: Print num lines of trailing context after matching lines\n" +
                " -i: Case-insensitive search\n" +
                " --help: Hint output\n" +
                " --exclude: Exclude files from the search\n" +
                "Example: grek -B 4 -A 5 -nr <regEx> <pathToFolder>")
    }


    private fun checkFolder(
        directory: Path,
        regex: String,
        lineNumber: Boolean,
        context: Pair<Int, Int>,
        recursive: Boolean,
        exclude: String,
        ignoreCase: Boolean
    ): String {

        val path = directory.listDirectoryEntries()
        val buf: MutableList<Path> = mutableListOf()

        val folder: Array<Path> = if (exclude.isNotEmpty()) {
            for (file in path) {
                if (exclude.toRegex().find(file.toString())!!.value.isEmpty()) {
                    buf.add(file)
                }
            }
            buf.toTypedArray()
        } else {
            directory.listDirectoryEntries().toTypedArray()
        }

        var matches = ""
        if (folder.isEmpty()) {
            return matches
        }

        for (element in folder) {
            if (element.isDirectory() && recursive) {
                matches += checkFolder(
                    element, regex, lineNumber, context,
                    recursive, exclude, ignoreCase
                )
            }
            if (element.isAbsolute) {
                val matchesInFile = checkFiles(
                    element, regex, lineNumber,
                    context, ignoreCase
                )
                matches += element.toString() + "\n" + matchesInFile
            }
        }
        return matches
    }


    private fun checkFiles(
        file: Path,
        regex: String,
        lineNumber: Boolean,
        context: Pair<Int, Int>,
        ignoreCase: Boolean
    ): String {

        val lines = file.readLines().toMutableList()
        var matches = ""
        val rgx: String
        val (before, after) = context

        if (ignoreCase) {
            rgx = regex.lowercase(Locale.getDefault())

            for ((i, line) in lines.withIndex()) {
                val lineLc: String = line.lowercase(Locale.getDefault())
                lines[i] = lineLc
            }
        } else {
            rgx = regex
        }

        for (i in lines.indices) {
            rgx.toRegex().find(lines[i])?.let {
                val left: Int = minOf(i + after, lines.size - 1)
                val right: Int = maxOf(i - before, 0)
                val interval = right..left
                for (j in interval) {
                    if (lineNumber) {
                        matches += (j + 1).toString() + ") "
                    }
                    matches += file.readLines().toMutableList()[j] + " \t"
                }
                matches += " \n"
            }
        }
        return matches
    }


    fun launch(params: Arguments): String {

        if (params.help) {
            println(helpFun())
        }

        val element = Paths.get(params.path)

        val context = Pair(params.before_context, params.after_context)
        val result: String = if (element.isDirectory()) {
            checkFolder(
                element, params.regex, params.line_number, context,
                params.recursive, params.exclude, params.ignore_case
            )
        } else {
            checkFiles(
                element, params.regex, params.line_number,
                context, params.ignore_case
            )
        }
        return result
    }
}
