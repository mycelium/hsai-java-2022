package grek.IO

import grek.FileIO
import grek.FileIO.GPath.Companion.asGPath
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

object OkioIO : FileIO {
    private suspend fun FileIO.GPath.fromGPath(): Path = path.toPath()

    private suspend fun Path.canonicalize() = FileSystem.SYSTEM.canonicalize(this)
    override suspend fun FileIO.GPath.exists(): Boolean = FileSystem.SYSTEM.exists(fromGPath())

    override suspend fun FileIO.GPath.isDirectory(): Boolean = FileSystem.SYSTEM.metadata(fromGPath()).isDirectory

    override suspend fun FileIO.GPath.listFiles(): Flow<FileIO.GPath> =
        FileSystem.SYSTEM.list(fromGPath()).asFlow().map { it.canonicalize().toString().asGPath() }

    override suspend fun FileIO.GPath.listRecursively(): Flow<FileIO.GPath> =
        FileSystem.SYSTEM.listRecursively(fromGPath()).asFlow().map { it.canonicalize().toString().asGPath() }

    override suspend fun FileIO.GPath.isFile(): Boolean = FileSystem.SYSTEM.metadata(fromGPath()).isRegularFile

    override suspend fun FileIO.GPath.fileName(): String = fromGPath().name

    override suspend fun FileIO.GPath.asAbsolutePath(): String = fromGPath().canonicalize().toString()

    override suspend fun FileIO.GPath.readFileLines(): Sequence<String> =
        FileSystem.SYSTEM.read(fromGPath()) { readUtf8() }.split('\n').asSequence()
}