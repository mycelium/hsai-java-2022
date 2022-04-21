package com.example.grek

import java.io.File
import kotlin.math.max
import kotlin.math.min

class Searcher(private val params: Grek) {

    fun search() {
        val regex = if (params.ignoreCase) {
            params.regex.toRegex(RegexOption.IGNORE_CASE)
        } else {
            params.regex.toRegex()
        }

        if (params.recursive) {
            params.file
                .walkTopDown()
                .filter { it.isFile }
                .filter { !params.listExclude.contains(it.name) }
                .forEach { searchInFile(it, regex) }
        } else {
            if (params.listExclude.contains(params.file.name)) return
            searchInFile(params.file, regex)
        }
    }

    private fun searchInFile(file: File, regex: Regex) {
        val lines = file.readLines()

        lines.forEachIndexed { i, line -> run {

                if (line.contains(regex)) {

                    max(0, i - params.beforeContext).until(i).forEach {
                        printFound(file.absolutePath, it, lines[it])
                    }

                    printFound(file.absolutePath, i, line)

                    (i + 1).until(min(lines.size, i + 1 + params.afterContext)).forEach {
                        printFound(file.absolutePath, it, lines[it])
                    }
                }
            }
        }
    }

    private fun printFound(absolutePath: String, numOfLine: Int, text: String) {
        println("$absolutePath${if (params.lineNumber) " $numOfLine " else " "}$text")
    }
}
