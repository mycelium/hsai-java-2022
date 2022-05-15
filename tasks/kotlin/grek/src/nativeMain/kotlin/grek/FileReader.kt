package grek

import okio.FileSystem
import okio.Path
import okio.buffer
import okio.use
import kotlin.native.concurrent.Future
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

class FileReader {
    private val filesData = mutableMapOf<Path, Sequence<String>>()

    fun readFiles(paths: Collection<Path>): Map<Path, Sequence<String>> {
        val futureMap: Map<Path, Future<Sequence<String>>> = paths.toList().associateWith { path -> readFile(path) }
        futureMap.forEach { pair -> filesData[pair.key] = pair.value.consume { it } }
        return filesData
    }

    private fun readFile(path: Path): Future<Sequence<String>> {
        val worker = Worker.start()
        return worker.execute(TransferMode.SAFE, { path.freeze() }, { path ->
            val fileContents = mutableListOf<String>()
            FileSystem.SYSTEM.source(path).use { fileSource ->
                fileSource.buffer().use { bufferedFileSource ->
                    while (true) {
                        fileContents.add(bufferedFileSource.readUtf8Line() ?: break)
                    }
                }
            }
            fileContents.asSequence().freeze()
        })
    }
}