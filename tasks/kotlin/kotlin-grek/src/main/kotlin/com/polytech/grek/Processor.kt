package com.polytech.grek

import java.io.File
import kotlin.math.max
import kotlin.math.min

object Processor {

    fun process(stringRegex: String, file: File, n: Boolean, r: Boolean, a: Int, b: Int, i: Boolean, exclude: List<String>) {
        val regex = if (i) stringRegex.toRegex(RegexOption.IGNORE_CASE) else stringRegex.toRegex()

        if (r) {
            file.walk()
                .filter { exclude.contains(it.name) }
                .filter { it.isFile }
                .toList()
        } else {
            listOf(file).filter { !exclude.contains(it.name) }
        }.forEach { search(it, regex, a, b, n) }
    }

    private fun search(file: File, regex: Regex, a: Int, b: Int, n: Boolean) {
        val lines = file.readLines()

        for (i in lines.indices) {
            if (lines[i].contains(regex)) {
                for (it in max(0, i - b).until(i)) {
                    printFound(file.absolutePath, it, lines[it], n)
                }

                printFound(file.absolutePath, i, lines[i], n)

                for (it in (i + 1).until(min(lines.size, i + 1 + a))) {
                    printFound(file.absolutePath, it, lines[it], n)
                }
            }
        }
    }

    private fun printFound(absolutePath: String, i: Int, line: String, n: Boolean) {
        if (n) println("$absolutePath $i $line")
        else println("$absolutePath $line")
    }
}