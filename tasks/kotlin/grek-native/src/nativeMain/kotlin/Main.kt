package grek

import arrow.core.Either
import arrow.core.computations.either
import grek.IO.OkioIO
import kotlinx.coroutines.runBlocking

/**
 * Grek application effects stack
 */
class GrekApp(
    options: Reader<Options>,
    fileIO: FileIO
) : Reader<Options> by options,
    FileIO by fileIO

/**
 * Main grek application function
 */
suspend fun <R> R.doGrek(): Either<GrekError, Unit>
        where R : Reader<Options>, R : FileIO = either<GrekError, Unit>
{
    val toProcess = getFilesToProcess().bind()
    val files = toProcess.toFiles()
    val grekkedFiles = processFiles(files)
    val printFileName = when (toProcess) {
        is FilesToProcess.MultipleFiles -> true
        is FilesToProcess.SingleFile -> false
    }
    for (grekkedFile in grekkedFiles) {
        renderGrek(grekkedFile, printFileName)
    }
}.tapLeft { grekError: GrekError -> println(grekError.render()) }

fun main(args: Array<String>) {
    fun runAppWithOptions(options: Options) {
        val grekApp = GrekApp(Reader.runReader(options), OkioIO)

        runBlocking { grekApp.doGrek() }
    }

    GrekCli(::runAppWithOptions).main(args)
}
