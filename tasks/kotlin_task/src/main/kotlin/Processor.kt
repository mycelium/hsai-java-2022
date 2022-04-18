import java.awt.Color

class Processor(reader: Reader) {

    var reader: Boolean = false

    fun singleFileProcessor(settings: Arguments, reader: Reader) {
        if (reader.checkExists(settings.path)) {
            val strings = reader.readFile(settings.path)
            findMatchedStrings(settings, strings, settings.path)
        } else println("Wrong file path")
    }

    fun directoryProcessor(settings: Arguments, reader: Reader) {
        if (reader.checkExists(settings.path)) {
            val filesStrings = reader.readDirectory(settings)
            for (curr in filesStrings)
                findMatchedStrings(settings, curr.value, curr.key)
        } else println("Wrong file path")
    }

    private fun findMatchedStrings(settings: Arguments, strings: List<String>, fileName: String) {
        val rgx = if (settings.registerIgnore) settings.pattern.toRegex(RegexOption.IGNORE_CASE)
        else settings.pattern.toRegex()

        for (i in strings.indices) {
            if (rgx.containsMatchIn(strings[i])) {
                println("Find in file $fileName")
                val GREEN = "\\u001B[32m"
                val RESET = "\\u001B[0m"
                if (settings.beforeRows != 0) {
                    for (j in Integer.max(0, i - settings.beforeRows) until i) {
                        println(GREEN + strings[j] + "\n" + RESET)
                    }
                }
                println(strings[i])
                if (settings.afterRows != 0) {
                    for (j in i+1..Integer.min(strings.size - 1, i + settings.afterRows)) {
                        println(GREEN + strings[j] + "\n" + RESET)
                    }
                }
            }
        }
    }
}