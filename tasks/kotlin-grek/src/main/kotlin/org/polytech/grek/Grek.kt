package org.polytech.grek

import java.io.File
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.max
import kotlin.math.min

class Grek(private val cmdParams: CmdParams) {

    fun findAll() {
        val regex = if (cmdParams.i) cmdParams.regex.toRegex(RegexOption.IGNORE_CASE) else cmdParams.regex.toRegex()

        val listFiles: List<File> = if (cmdParams.r) {
            cmdParams.path.toFile().walk()
                .filter { it.isFile }
                .filter { !cmdParams.exclude.contains(it.name) }
                .toList()
        } else {
            listOf(cmdParams.path.toFile())
                .filter { !cmdParams.exclude.contains(it.name) }
        }

        listFiles.forEach {
            find(it.absolutePath.toString(), it.readLines(), regex)
        }
    }

    private fun find(fileName: String, lines: List<String>, regex: Regex) {
        for (i in lines.indices) {
            if (lines[i].contains(regex)) {
                (max(i - cmdParams.b, 0) until i).forEach { output(fileName, it, lines[it], "-") }
                output(fileName, i, lines[i], ":")
                ((i + 1) until min(i + 1 + cmdParams.a, lines.size)).forEach { output(fileName, it, lines[it], "-") }
            }
        }
    }

    private fun output(fileName: String, num: Int, line: String, separator: String) {
        println(Stream.of(fileName, if (cmdParams.n) num else null, line)
            .filter { it != null }
            .map { it.toString() }
            .collect(Collectors.joining(separator)))
    }
}
