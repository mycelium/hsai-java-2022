package com.example.grek

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    Grek().main(args)
}

class Grek : CliktCommand() {

    val regex: String by argument(help = "Regexp to match files")
    val file: File by argument(help = "Path to search in").file(mustExist = true)

    val lineNumber: Boolean by option("-n", "--line-number", help = "Print line number").flag()
    val recursive: Boolean by option("-r", "--recursive", help = "Recursively search subdirectories listed").flag()
    val afterContext: Int by option("-A", "--after-context", help = "Print num lines of trailing context after each match").int().default(0)
    val beforeContext: Int by option("-B", "--before-context", help = "Print num lines of leading context before each match").int().default(0)

    val ignoreCase: Boolean by option("-i", "--ignore-case", help = "Perform case insensitive matching. By default, grek is case sensitive").flag()
    val listExclude: List<String> by option("--exclude", help = "If specified, it excludes files matching the given filename").multiple()

    override fun run() {
        checkInput()
        Searcher(this).search()
    }

    private fun checkInput() {
        when {
            !recursive && file.isDirectory -> exitWithMessage("Path $file is a directory")
            afterContext < 0 || beforeContext < 0 -> exitWithMessage("A and B must be at least 0")
        }
    }

    private fun exitWithMessage(msg: String) {
        System.err.println(msg)
        exitProcess(0)
    }
}
