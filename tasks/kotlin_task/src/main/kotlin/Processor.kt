import kotlin.system.exitProcess

class Processor(reader: Reader) {

    var reader: Boolean = false

    fun singleFileProcessor(settings: Arguments, reader: Reader) {
        if (reader.checkExists(settings.path)) {
            val strings = reader.readFile(settings.path)
            findMatchedStrings(settings, strings, settings.path)
        } else {
            println("Wrong file path")
            exitProcess(-1)
        }
    }

    fun directoryProcessor(settings: Arguments, reader: Reader) {
        if (reader.checkExists(settings.path)) {
            val filesStrings = reader.readDirectory(settings)
            for (curr in filesStrings)
                findMatchedStrings(settings, curr.value, curr.key)
        } else {
            println("Wrong file path")
            exitProcess(-1)
        }
    }

    private fun findMatchedStrings(settings: Arguments, strings: List<String>, fileName: String) {
        val rgx = if (settings.registerIgnore) settings.pattern.toRegex(RegexOption.IGNORE_CASE)
        else settings.pattern.toRegex()

        for (i in strings.indices) {
            if (rgx.containsMatchIn(strings[i])) {
                println("Find in file $fileName")
                val red = "\u001b[31m"
                val reset = "\u001b[0m"
                if (settings.beforeRows != 0) {
                    for (j in Integer.max(0, i - settings.beforeRows) until i) {
                        println(strings[j])
                    }
                }
                println(red + strings[i] + reset)
                if (settings.afterRows != 0) {
                    for (j in i + 1..Integer.min(strings.size - 1, i + settings.afterRows)) {
                        println(strings[j])
                    }
                }
            }
        }
    }
}