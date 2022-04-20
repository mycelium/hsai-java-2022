package org.polytech.grek

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import java.nio.file.Path
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    App().main(args)
}

data class CmdParams(
    val n: Boolean,
    val r: Boolean,
    val a: Int,
    val b: Int,
    val i: Boolean,
    val exclude: List<String>,
    val regex: String,
    val path: Path
) { }

class App : CliktCommand() {
    private val n: Boolean by option(names = arrayOf("-n", "--line-number"), help = "Each output line is preceded by its relative line number in the file, starting at line 1.  The line number counter is reset for each file processed.").flag()
    private val r: Boolean by option(names = arrayOf("-R", "-r", "--recursive"), help = "Recursively search subdirectories listed.").flag()
    private val a: Int by option(names = arrayOf("-A", "--after-context"), help = "Print num lines of trailing context after each match.").int().default(0)
    private val b: Int by option(names = arrayOf("-B", "--before-context"), help = "Print num lines of leading context before each match.").int().default(0)
    private val i: Boolean by option(names = arrayOf("-i", "--ignore-case"), help = "Perform case insensitive matching. By default, grek is case sensitive.").flag()
    private val exclude: List<String> by option(names = arrayOf("--exclude"), help = "If specified, it excludes files matching the given filename.").multiple()

    private val regex: String by argument(help = "Regexp to match files.")
    private val path: Path by argument(help = "Path to search in.").path()

    override fun run() {
        echo("a = $a, b = $b, n = $n, r = $r, i = $i, exclude = $exclude, regex = $regex, path = $path")
        val cmdParams = CmdParams(n, r, a, b, i, exclude, regex, path)

        try {
            Validator.validate(cmdParams)
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message)
            exitProcess(0)
        }

        Grek(cmdParams).findAll()
    }
}
