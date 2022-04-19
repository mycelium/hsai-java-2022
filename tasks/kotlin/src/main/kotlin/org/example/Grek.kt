package org.example

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

class Grek :CliktCommand() {

    val n by option("-n", "--line-number", help= "Prefix each line of output with  the  1-based  line  number within its input file.").flag()
    val r: Boolean by option("-r", "--recursive", help = "Read all files under each directory, recursively").flag()
    val a: Int by option("-A", "--after-context", help = "Print NUM lines of trailing context after  matching  lines.").int().default(0)
    val b: Int by option("-B", "--before-context", help = " Print NUM lines of leading context before  matching  lines.").int().default(0)
    val i: Boolean by option("-i", "--ignore-case", help = "Ignore  case  distinctions,  so that characters that differ + only in case match each other.").flag()

    val exclude: List<String> by option("-e","--exclude", help = "Skip  any command-line file with a name suffix that matches the pattern GLOB").multiple()
    val regex: String  by argument(help ="Regular expression for files")
    val path: Path by argument(help = "Path to single file or folder").path()

    override fun run() {
        echo("a = $a, b = $b, n = $n, r = $r, i = $i, exclude = $exclude, regex = $regex, path = $path")
        if(!Checker.check(this)){
            println("Illegal option!")
            exitProcess(0)
        }
        val searcher = Searcher(this)
        searcher.search()
    }

}


