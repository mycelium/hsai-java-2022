package grek

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.split

suspend fun <R> R.renderGrek(file: ProcessedFile, printFileName: Boolean) where R : HasOptions =
    // Do only if seq not null
    file.matchingLines.lines.split()?.let {
        val lines = sequenceOf(it.second) + it.first

        // Print file name when processing folder
        if (printFileName)
            println(file.filePath)

        for (line in lines) {
            println(this.renderOpts.renderLine(line))
        }
    }


suspend fun <R> R.doGrek(): Either<FileError, Unit> where R : HasOptions = either {
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
}

fun main(args: Array<String>) = GrekCli().main(args)