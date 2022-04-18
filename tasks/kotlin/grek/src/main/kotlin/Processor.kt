import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.pathString
import kotlin.io.path.readLines
import kotlin.streams.toList

object Processor {

    fun process() {
        if (flags["help"] == true) {
            return
        }
        val regex: Regex = if (flags["case"] == false)
            values["regex"]!!.toRegex()
        else
            values["regex"]!!.toRegex(RegexOption.IGNORE_CASE)
        var response = listOf<String>()
        if (flags["file"] == true) {
            val file = File(values["path"]!!)
            try {
                response = search(file.readLines(), regex)
            } catch (IOE: IOException) {
                println(IOE.message)
            }
            if (response.isNotEmpty()) {
                CLI.printResponse(response, file.absolutePath)
            }
        } else {
            try {
                val files: List<Path> = Files.walk(Paths.get(values["path"]!!)).toList()
                    .minus(values["exclude"]!!.split("|").stream().map(Paths::get).toList().toSet())
                for (file in files) {
                    if (!File(file.toUri()).isDirectory) {
                        response = search(file.readLines(), regex)
                        if (response.isNotEmpty()) {
                            CLI.printResponse(response, file.pathString)
                        }
                    }
                }
            } catch (IOE: IOException) {
                println(IOE.stackTraceToString())
            }
        }
    }

    private fun search(stream: List<String>, regex: Regex): List<String> {
        val response = mutableListOf<String>()
        for (i in stream.indices) {
            if (stream[i].contains(regex)) {
                response.add(">>>>>")
                for (j in Integer.max(0, i - Integer.parseInt(values["before"]!!)) until i) {
                    response.add(stream[j])
                }
                response.add("> " + stream[i] + " <")
                for (j in i+1..Integer.min(stream.size - 1, i + Integer.parseInt(values["after"]!!))) {
                    response.add(stream[j])
                }
                response.add("<<<<<")
            }
        }
        return response
    }
}