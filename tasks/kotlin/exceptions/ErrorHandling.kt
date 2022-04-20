package exceptions

import arguments.fillArgs
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

fun handleErrors(args: Array<String>) {
    if (args.isEmpty()) {
        throw Exception("No arguments found")
    }

    try {
        fillArgs(args)
    } catch (e: Exception) {
        println("Not enough arguments: regex and path are required")
    }

    if (fillArgs(args).after_context < 0 || fillArgs(args).before_context < 0){
        throw Exception("Context cannot be negative")
    }

    val element = Paths.get(fillArgs(args).path)
    if (!element.exists() || !element.isDirectory() && !element.isAbsolute) {
        throw IllegalArgumentException("Path was not found")
    }
}
