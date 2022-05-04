import java.io.File

data class Context(val file: File,
                   val regex: Regex,
                   var n: Boolean = false, //if true add number of string to result
                   var r: Boolean = false, //if true add filename to result
                   var A: Int = 0,
                   var B: Int = 0,
                   var relativeFileName: String)