package grek

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.fx.coroutines.parSequence
import java.io.File

suspend fun getFilesToProcessFolder(path: File, folderOpts: FolderOpts): Either<GrekError, ToProcess> =
    if (path.isDirectory) {
        val files =
            if (folderOpts.recursive)
                path.walkBottomUp()
            else
                path.listFiles().asSequence()
        val isNotExcluded = { file: File -> !folderOpts.exclude.contains(file.name) }
        val isNotHiddenFile = { file: File -> file.isFile && !file.isHidden }
        val filesToProcess = files.filter { isNotHiddenFile(it) && isNotExcluded(it) }.toList()
        ToProcess.MultipleFiles(filesToProcess).right()
    } else
        GrekError.PathIsNotDirectory("Path is not a directory: ${path.absolutePath}").left()

suspend fun <R> R.getFilesToProcess(): Either<GrekError, ToProcess> where R : Reader<Options> {
    val fileOpts = this.ask.filesOpts
    val path = File(fileOpts.path)
    return if (path.exists()) {
        if (!path.isDirectory)
            ToProcess.SingleFile(path).right()
        else
            getFilesToProcessFolder(path, fileOpts.folderOpts)
    } else
        GrekError.PathNotExists("Path doesn't exists: ${fileOpts.path}").left()
}

suspend fun <R> R.processFile(file: File): ProcessedFile where R : Reader<Options> {
    val lines = file.readLines()
    return processFileContents(file.canonicalPath, lines)
}

suspend fun <R> R.processFiles(files: List<File>) where R : Reader<Options> =
    files.map { file -> suspend { processFile(file) } }.parSequence()