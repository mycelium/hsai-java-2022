package com.polytech.grek

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

fun main(args: Array<String>) {
    Main().main(args)
}

class Main : CliktCommand() {

    private val regex: String by argument(help = "Regex for search in files")
    private val path: Path by argument(help = "File or directory to search in").path()

    private val n: Boolean by option("-n", help = "Print line number of found line").flag()
    private val r: Boolean by option("-r", help = "Search in subdirectories").flag()
    private val a: Int by option("-A", help = "Print num lines after found line").int().default(0)
    private val b: Int by option("-B", help = "Print num lines before found line").int().default(0)

    private val i: Boolean by option("-i", help = "Ignore case REGEX").flag()
    private val exclude: List<String> by option("--exclude", help = "Filenames not to be searched").multiple()

    override fun run() {
        try {
            validateInput()
        } catch (ex: Exception) {
            println("Input error: ${ex.message}")
        }

        Processor.process(regex, path.toFile(), n, r, a, b, i, exclude)
    }

    private fun validateInput() {
        when {
            !path.exists() -> throw IllegalArgumentException("path $path doesn't exist!")
            !r && path.isDirectory() -> throw IllegalArgumentException("without -r path must not be a directory!")
            a < 0 || b < 0 -> throw IllegalArgumentException("numbers -A and -B must not be less than zero!")
        }
    }
}