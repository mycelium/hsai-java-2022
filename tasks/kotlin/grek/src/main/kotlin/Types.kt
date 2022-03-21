package grek

import java.io.File

sealed class FileError {
    class PathNotExists(val str: String) : FileError()
    class PathIsNotDirectory(val str: String) : FileError()
}

data class FilesOpts(val path: String, val folderOpts: FolderOpts)
data class FolderOpts(val recursive: Boolean, val exclude: Set<String>)
data class WindowOpts(val before: Int, val after: Int)

sealed class RenderOpts {
    abstract fun renderLine(line: Line): String

    object RenderSimple : RenderOpts() {
        override fun renderLine(line: Line): String = line.value
    }

    object RenderRow : RenderOpts() {
        override fun renderLine(line: Line): String {
            val delim = if (line.matched) ':' else '-'
            return "${line.index}${delim}${line.value}"
        }
    }
}

interface HasOptions {
    val regexOpt: Regex
    val filesOpts: FilesOpts
    val windowOpts: WindowOpts
    val renderOpts: RenderOpts
}

sealed class ToProcess {
    class SingleFile(val file: File) : ToProcess()
    class MultipleFiles(val files: List<File>) : ToProcess()

    fun toFiles() = when (this) {
        is MultipleFiles -> this.files
        is SingleFile -> listOf(this.file)
    }
}

data class Line(val index: Int, val value: String, val matched: Boolean)

data class MatchingLines(val lines: Sequence<Line>)

data class ProcessedFile(val filePath: String, val matchingLines: MatchingLines)