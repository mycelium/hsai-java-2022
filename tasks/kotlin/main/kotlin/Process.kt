import java.nio.file.Path
import java.util.*
import arguments.*
import java.nio.file.Paths
import kotlin.io.path.*

class Process {
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
        line_number: Boolean,
        context: Pair <Int, Int>,
        recursive: Boolean,
        exclude: String,
        ignore_case: Boolean
    ): String {

        val path = directory.listDirectoryEntries()
        var buf: MutableList<Path> = mutableListOf<Path>()
        val folder: Array<Path>

        // Form array of files from folder to be analyzed
        if(!exclude.isEmpty()){ // exclude - regular expression
            for(file in path){
                // not found file to exclude
                if (exclude.toRegex().find(file.toString())!!.value.isEmpty())
                {
                    buf.add(file)
                }
            }
            folder = buf.toTypedArray()
        } else {
            folder = directory.listDirectoryEntries().toTypedArray()
        }

        var matches: String = ""
        if (folder.isEmpty()) {
            return matches
        }

        for (element in folder) {
            if (element.isDirectory() && recursive) {
                matches += checkFolder(
                    element, regex, line_number, context,
                    recursive, exclude, ignore_case)
            }
            if (element.isAbsolute) {
                val matchesInFile = checkFiles(
                    element, regex, line_number,
                    context, exclude, ignore_case)
                    matches += element.toString() + "\n" + matchesInFile
            }
        }
        return matches
    }


    private fun checkFiles(
        file: Path,
        regex: String,
        line_number: Boolean,
        context: Pair<Int, Int>,
        exclude: String,
        ignore_case: Boolean
    ): String {

        val lines = file.readLines().toMutableList()
        var matches: String = ""
        val rgx: String
        val (before, after) = context

        // String case processing
        if (ignore_case) {
            rgx = regex.lowercase(Locale.getDefault())

            var i = 0
            for (line in lines){
                var line_lc = line
                line_lc = line.lowercase(Locale.getDefault())
                lines[i] = line_lc
                i++
            }
        } else {
            rgx = regex
        }

        // Search with regular expression
        for(i in lines.indices) {
            rgx.toRegex().find(lines.get(i))?.let {
                val left: Int = minOf(i + after, lines.size - 1)
                val right: Int = maxOf(i - before, 0)
                val interval = right..left
                for (j in interval) {
                    if (line_number) {
                        matches += (j + 1).toString() + ") "
                    }
                    matches += file.readLines().toMutableList()[j] + " \t"
                }
                matches += " \n"
            }
        }
        return matches
    }


    fun launch(params: Args): String {

        if (params.help) {
            println(helpFun())
        }

        // File/folder existence check
        val element = Paths.get(params.path)

        // Run main functionality: check folders and files
        val context = Pair(params.before_context, params.after_context)
        val result: String
        if (element.isDirectory()) {
            result = checkFolder(
                element, params.regex, params.line_number, context,
                params.recursive, params.exclude, params.ignore_case)
        } else {
            result = checkFiles(
                element, params.regex, params.line_number,
                context, params.exclude, params.ignore_case)
        }
        return result
    }
}
