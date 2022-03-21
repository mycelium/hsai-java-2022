package grek

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.runBlocking

class GrekCli : CliktCommand() {
    val n: Boolean by option("-n", help = "enable line numbers").flag()
    val r: Boolean by option("-r", help = "recursive folder mode").flag()
    val A: Int by option("-A", help = "print NUM lines of trailing context after matching lines").int().default(0)
    val B: Int by option("-B", help = "print NUM lines of leading context before matching lines").int().default(0)
    val i: Boolean by option("-i", help = "case-insensitive mode").flag()
    val exclude: List<String> by option("--exclude", help = "exclude files").multiple()
    val regex by argument(help = "regexp to match files")
    val path by argument(help = "path to search in")

    override fun run() {
        val toRegexWithConfig = { regStr: String ->
            if (i) regStr.toRegex(RegexOption.IGNORE_CASE)
            else regStr.toRegex()
        }
        val folderConfig = FolderOpts(recursive = r, exclude = exclude.toSet())
        val withConfig = object : HasOptions {
            override val regexOpt: Regex
                get() = toRegexWithConfig(regex)
            override val filesOpts: FilesOpts
                get() = FilesOpts(path, folderConfig)
            override val windowOpts: WindowOpts
                get() = WindowOpts(A, B)
            override val renderOpts: RenderOpts
                get() = if (n) RenderOpts.RenderRow else RenderOpts.RenderSimple

        }
        runBlocking { withConfig.doGrek() }
    }
}


