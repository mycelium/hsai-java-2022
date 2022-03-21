package grek

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.fx.coroutines.parSequence
import java.io.File

suspend fun getFilesToProcessFolder(path: File, folderOpts: FolderOpts): Either<FileError, ToProcess> =
    if (path.isDirectory) {
        val files =
            if (folderOpts.recursive)
                path.walkBottomUp()
            else
                path.listFiles().asSequence()
        val isNotExcluded = { file: File -> !folderOpts.exclude.contains(file.name) }
        val isNotHiddenFile = { file: File -> file.isFile && !file.isHidden }
        val filesToProcess = files.filter { isNotHiddenFile(it) && isNotExcluded(it) }.toList()
        Either.Right(ToProcess.MultipleFiles(filesToProcess))
    } else
        Either.Left(FileError.PathIsNotDirectory("Path is not a directory: ${path.absolutePath}"))

suspend fun <R> R.getFilesToProcess(): Either<FileError, ToProcess> where R : HasOptions {
    val fileOpts = this.filesOpts
    val path = File(fileOpts.path)
    return if (path.exists()) {
        if (!path.isDirectory)
            ToProcess.SingleFile(path).right()
        else
            getFilesToProcessFolder(path, fileOpts.folderOpts)
    } else
        FileError.PathNotExists("Path doesn't exists: ${fileOpts.path}").left()
}

suspend fun <R> R.processFile(file: File): ProcessedFile where R : HasOptions {
    val lines = file.readLines()
    return processFileContents(file.absolutePath, lines)
}

suspend fun <R> R.processFiles(files: List<File>) where R : HasOptions =
    files.map { file -> suspend { processFile(file) } }.parSequence()