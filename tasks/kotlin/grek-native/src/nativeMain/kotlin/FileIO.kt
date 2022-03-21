package grek

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.fx.coroutines.parSequence
import grek.FileIO.GPath.Companion.asGPath
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList

suspend fun <R> R.getFilesToProcessFolder(
    path: FileIO.GPath,
    folderOpts: FolderOpts
): Either<GrekError, FilesToProcess> where R : FileIO {
    val files =
        if (folderOpts.recursive)
            path.listRecursively()
        else
            path.listFiles()
    val isNotExcluded = { file: FileIO.GPath -> !folderOpts.exclude.contains(file.path) }
    val filesToProcess = files.filter { it.isFile() && isNotExcluded(it) }.toList()
    return FilesToProcess.MultipleFiles(filesToProcess).right()
}

suspend fun <R> R.getFilesToProcess(): Either<GrekError, FilesToProcess> where R : Reader<Options>, R : FileIO {
    val fileOpts = this.ask.filesOpts
    val path = fileOpts.path.asGPath()
    return if (path.exists()) {
        if (path.isDirectory())
            getFilesToProcessFolder(path, fileOpts.folderOpts)
        else
            FilesToProcess.SingleFile(path).right()
    } else
        GrekError.PathNotExists("Path doesn't exists: ${fileOpts.path}").left()
}

suspend fun <R> R.processFile(file: FileIO.GPath): ProcessedFile where R : Reader<Options>, R : FileIO {
    val lines = file.readFileLines()
    return processFileContents(file.asAbsolutePath(), lines)
}

suspend fun <R> R.processFiles(files: List<FileIO.GPath>) where R : Reader<Options>, R : FileIO =
    files.map { file -> suspend { processFile(file) } }.parSequence()