package grek

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.split
import kotlinx.coroutines.runBlocking

suspend fun <R> R.renderGrek(file: ProcessedFile, printFileName: Boolean) where R : Reader<Options> =
    // Do only if seq not null
    file.matchingLines.lines.split()?.let {
        val lines = sequenceOf(it.second) + it.first

        // Print file name when processing folder
        if (printFileName) println(file.filePath)

        for (line in lines) {
            println(this.ask.renderOpts.renderLine(line))
        }
    }


suspend fun <R> R.doGrek(): Either<GrekError, Unit> where R : Reader<Options> = either<GrekError, Unit> {
    val toProcess = getFilesToProcess().bind()
    val files = toProcess.toFiles()
    val grekkedFiles = processFiles(files)
    val printFileName = when (toProcess) {
        is ToProcess.MultipleFiles -> true
        is ToProcess.SingleFile -> false
    }
    for (grekkedFile in grekkedFiles) {
        renderGrek(grekkedFile, printFileName)
    }
}.tapLeft { grekError: GrekError -> println(grekError.render()) }


fun main(args: Array<String>) {
    fun runAppWithOptions(options: Options) {
        val grepApp = GrepApp(Reader.runReader(options))

        runBlocking { grepApp.doGrek() }
    }

    GrekCli(::runAppWithOptions).main(args)
}