package grek

import kotlinx.coroutines.flow.Flow

// Reader effect, ReaderT imitation
interface Reader<Env> {
    val ask: Env

    companion object {
        private class ReaderImpl<Env>(override val ask: Env) : Reader<Env>

        fun <Env> runReader(env: Env): Reader<Env> = ReaderImpl(env)
    }
}

// IO effect
interface FileIO {
    interface GPath {
        val path: String

        companion object {
            class GPathImpl(override val path: String) : GPath

            fun String.asGPath(): GPath = GPathImpl(this)
        }
    }

    suspend fun GPath.exists(): Boolean
    suspend fun GPath.isDirectory(): Boolean
    suspend fun GPath.listFiles(): Flow<GPath>
    suspend fun GPath.listRecursively(): Flow<GPath>
    suspend fun GPath.isFile(): Boolean
    suspend fun GPath.fileName(): String
    suspend fun GPath.asAbsolutePath(): String
    suspend fun GPath.readFileLines(): Sequence<String>
}