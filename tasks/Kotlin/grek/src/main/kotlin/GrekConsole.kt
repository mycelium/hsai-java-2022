package grek

import picocli.CommandLine
import picocli.CommandLine.ArgGroup
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.io.*

@CommandLine.Command(name = "grek", mixinStandardHelpOptions = true, version = ["grek 1.0"], description = ["Use the regular expression to get satisfied strings from the files."])
class GrekConsole: Runnable{
    @Parameters(index = "0", description = ["Regular expression for the satisfied string."])
    lateinit var regex: String
    @Parameters(index = "1", description = ["Path of the file or the folder of the files to be scanned."])
    lateinit var path: File
    @Option(names = ["-n", "--line-number"], description = ["Show line and file name of satisfied strings."])
    var bShowLine = false
    @ArgGroup(exclusive = false)
    lateinit var directoryOptions: DirectoryOptions
    class DirectoryOptions{
        @Option(names = ["-r", "--recursive"], description = ["Recursively scan all the files in the folder."], required = true)
        var bRecurseAll = false
        @Option(names = ["--exclude"], description = ["The exclusive paths not to scan.\n(In format like \"a\\b.txt a\\a.txt\")"], required = false)
        var exclusivePathsAll: String = ""
    }
    @Option(names = ["-i", "--ignore-case"], description = ["Ignore case shifting."])
    var bIgnoreCase = false
    @Option(names = ["-B", "--before-context"], description = ["Print beforeNum lines of leading context before matching lines."])
    var beforeNum: Int = 0
    @Option(names = ["-A", "--after-context"], description = ["Print afterNum lines of trailing context after matching lines."])
    var afterNum: Int = 0
    private fun showInfo(dir: String){
        val textList = FileInputStream(dir).bufferedReader().readLines()
        for(i in textList.indices){
            val line = textList[i]
            if ((if(bIgnoreCase) regex.lowercase().toRegex() else regex.toRegex()).containsMatchIn(if(bIgnoreCase)line.lowercase() else line)) {
                var extraInfo = ""
                if(beforeNum!=0){
                    extraInfo+="Strings before the satisfied line:\n"
                    val before = (if(i-beforeNum>=0) textList.subList(i-beforeNum, i) else textList.subList(0, i)).joinToString("\n")
                    extraInfo+=before+"\n"
                }
                if(afterNum!=0){
                    extraInfo+="Strings after the satisfied line:\n"
                    val after = (if(i+afterNum<textList.size) textList.subList(i, i+afterNum) else textList.subList(i, textList.size)).joinToString("\n")
                    extraInfo+=after+"\n"
                }
                if (bShowLine) {
                    println("$dir Line $i:      $line")
                } else {
                    println(line)
                }
                if(extraInfo!="") {
                    println(extraInfo)
                }
            }
        }
    }
    @Throws(IOException::class)
    override fun run(){
        val exclusivePaths = directoryOptions.exclusivePathsAll.split("\\s".toRegex())
        exclusivePaths.forEach {
            if(File(it).isDirectory){
                println("$it is a directory but file path is needed for --exclude.")
                return
            }
        }
        if(!path.exists()){
            println("Path $path does not exist!")
            return
        }
        if(path.isDirectory && !directoryOptions.bRecurseAll){
            println("To scan the folder, need the option -r.")
            return
        }
        if(!path.isDirectory && directoryOptions.bRecurseAll){
            println("The path is not folder and not satisfied with the option -r.")
            return
        }
        if(directoryOptions.bRecurseAll){
            path.walk().forEach { dir ->
                if(!dir.isDirectory and (dir.toString() !in exclusivePaths)){
                    showInfo(dir.toString())
                }
            }
        }
        else{
            showInfo(path.toString())
        }
    }
}
fun main(args: Array<String>){
    CommandLine(GrekConsole()).execute(*args)
}