import java.nio.file.Files
import java.nio.file.Paths
import java.util.TreeMap

class Grek(val data: InputData) {
    val helpInfo = "Usage: grek [OPTION] PATTERN INPUT\n" +
            "Search for PATTERN in INPUT\n" +
            "PATTERN is regular expression\n\n" +
            "Options:\n" +
            "  -n\t\t\t\t\t\tprint line number with output lines\n" +
            "  -r\t\t\t\t\t\tsearch recursivly in directory INPUT\n" +
            "  -A NUM\t\t\t\t\tprint NUM lines of trailing context\n" +
            "  -B NUM\t\t\t\t\tprint NUM lines of leading context\n" +
            "  -i\t\t\t\t\t\tignore case distinctions\n" +
            "  --exclude FILE_PATTERN\tskip files and directories matching FILE_PATTERN\n" +
            "  --help\t\t\t\t\tdisplay this help text and exit"

    fun find() {
        if (data.errorMessage.isNotEmpty()) {
            val message = data.errorMessage
            println("grek: $message\nTry 'grek --help' for more information")
        }
        else if (data.help) {
            println(helpInfo)
        } else {
            if (data.recursive) {
                workWithDirectory()
            } else {
                workWithFile()
            }
        }
    }

    fun workWithDirectory() {
        if (Files.exists(Paths.get(data.input))) {
            if (Files.isDirectory(Paths.get(data.input))) {
                val files = getAllFilesInDirectory(data.input)
                for (f in files) {
                    val foundSth = fileWork(f, true)
                    if (!(f === files[files.lastIndex]) && foundSth)
                        println("--")
                }
            } else {
                println(data.input + " is not a directory")
            }
        } else {
            println(data.input + " doesn't exist")
        }
    }

    fun workWithFile() {
        if (Files.exists(Paths.get(data.input))) {
            if (!Files.isDirectory(Paths.get(data.input))) {
                fileWork(data.input, false)
            } else {
                println(data.input + " is not a file")
            }
        } else {
            println(data.input + " doesn't exist")
        }
    }

    //file - путь к файлу, writeFile - true: надо выводить путь к файлу
    //возвращаемое значение - true, если было совпадение
    fun fileWork(file: String, writeFile: Boolean): Boolean {
        if (data.exclude.isNotEmpty() && Paths.get(file).toFile().name.contains(Regex(data.exclude))) {
            return false
        }
        var matchLines = ArrayList<Int>()
        val lines = Files.readAllLines(Paths.get(file))
        var currentLine = 1
        val reg = if (data.ignore) Regex(data.regExp, RegexOption.IGNORE_CASE) else Regex(data.regExp)
        for (line in lines) {
            if (line.contains(reg)) {
                matchLines.add(currentLine)
            }
            currentLine++
        }
        if(matchLines.isEmpty()) {
            return false
        }
        val intervals = getIntervals(matchLines, lines.size)
        writeContent(file, intervals, matchLines, writeFile)
        return true
    }

    //file - путь к файлу, intervals - с какой строки по какую нужно вывести
    //matchLines - список строк, в которых было совпадение
    //writeFile - true:надо вывести путь к файлу
    fun writeContent(file: String, intervals: TreeMap<Int, Int>, matchLines: List<Int>, writeFile: Boolean) {
        val lines = Files.readAllLines(Paths.get(file))
        var startLines = intervals.keys.toList()
        for (start in startLines) {
            for (current in start..intervals.getValue(start)) {
                if (writeFile) {
                    print(file)
                    if (matchLines.contains(current)) {
                        print(":")
                    } else {
                        print("-")
                    }
                }
                if (data.numerate) {
                    print(current)
                    if (matchLines.contains(current)) {
                        print(":")
                    } else {
                        print("-")
                    }
                }
                println(lines.get(current - 1))
            }
            if ((data.after != -1 || data.before != -1) && !(start === startLines[startLines.lastIndex]))
                println("--")
        }
    }

    //dir - директория или файл
    //возвращаемое значение - список путей к файлам директории
    fun getAllFilesInDirectory(dir: String): List<String> {
        var result = ArrayList<String>()
        if (Files.isDirectory(Paths.get(dir))) {
            var files = Files.list(Paths.get(dir))
            for (f in files) {
                result.addAll(getAllFilesInDirectory(f.toFile().path))
            }
        } else {
            result.add(dir)
        }
        return result
    }

    //matchLines - список строк, в которых было совпадение, lines - количество строк в файле
    //возвращаемое значение - интервалы <первая строка, последняя строка>
    fun getIntervals(matchLines: List<Int>, lines: Int): TreeMap<Int, Int> {
        var result = TreeMap<Int, Int>()
        val after = if (data.after != -1) data.after else 0
        val before = if (data.before != -1) data.before else 0
        //поиск всех интервалов
        for (line in matchLines) {
            val begin = if (line - before < 1) 1 else line - before
            val end = if (line + after > lines) lines else line + after
            result.put(begin, end)
        }
        var idx = 0
        var keys = result.keys.toList()
        //объединение интервалов
        while (idx < result.size - 1) {
            if (keys[idx + 1] >= keys[idx] &&
                keys[idx + 1] <= result.getValue(keys[idx]) ||
                keys[idx + 1] == result.getValue(keys[idx]) + 1
            ) {
                val newValue = result.getValue(keys.get(idx + 1))
                result.remove(keys.get(idx + 1))
                result.put(keys.get(idx), newValue)
                keys = result.keys.toList()
            } else idx++
        }
        return result
    }
}
