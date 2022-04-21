import java.io.File
import java.util.*

class Commands {
    fun checkDirectory(directory: File, regex: String, lineNumber: Boolean, context: Pair<Int, Int>,
                       recursive: Boolean, exclude: String, ignoreCase: Boolean): String {

        val dirFiles = directory.listFiles()
        var temp: MutableList<File> = mutableListOf<File>()
        val files: Array<File>

        if(!exclude.isEmpty()){
            for(file in dirFiles){
                if (exclude.toRegex().find(file.toString())!!.value.isEmpty())
                {
                    temp.add(file)
                }
            }
            files = temp.toTypedArray()
        }
        else {
            files = directory.listFiles()
        }

        if (files.isEmpty()) {
            return ""
        }

        var str = ""
        for (file in files) {
            if (file.isDirectory && recursive) {
                str += checkDirectory(file, regex, lineNumber, context, recursive, exclude, ignoreCase)
            }
            if (file.isFile) {
                val res = checkFile(file, regex, lineNumber, context, exclude, ignoreCase)
                if (!res.isEmpty()) {
                    str += "${file}\n${res}"
                }
            }
        }
        return str
    }

    fun checkFile(file: File, regex: String, printLines: Boolean, context: Pair<Int, Int>,
                  exclude: String, ignoreCase: Boolean): String {
        val (beforeBound, afterBound) = context
        val lines = file.readLines().toMutableList()
        val rgx: String

        if (ignoreCase){
            rgx = regex.lowercase(Locale.getDefault())

            var i = 0
            for (line in lines){
                lines[i] = line.lowercase(Locale.getDefault())
                i++
            }
        }
        else {
            rgx = regex
        }

        var str = ""
        for(lineIndex in lines.indices) {
            rgx.toRegex().find(lines.get(lineIndex))?.let {
                val range = maxOf(lineIndex - beforeBound, 0)..minOf(lineIndex + afterBound, lines.size - 1)

                for (contextLineIndex in range) {
                    if (printLines) {
                        str += "${contextLineIndex + 1}: "
                    }
                    str += lines[contextLineIndex]
                }
                str += "\n"
            }
        }
        return str
    }

    fun helpList(): String{
        println("\n Commands\n" +
                "\n ---------------------------------------------------------------------\n" +
                " -n: line number\n" +
                " -nr: process all files in directory\n" +
                " -B: print num lines of leading context before matching lines\n" +
                " -A: print num lines of trailing context after matching lines\n" +
                " -i: case-insensitive search\n" +
                " --help: hint output\n" +
                " --exclude: exclude files from search\n")
    }
}
