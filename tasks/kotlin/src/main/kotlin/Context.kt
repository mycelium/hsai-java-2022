import java.io.File

data class Context(val file: File,
                   val regex: Regex,
                   var n: Boolean = false,
                   var r: Boolean = false,
                   var A: Int = 0,
                   var B: Int = 0,
                   var relativeFileName: String)