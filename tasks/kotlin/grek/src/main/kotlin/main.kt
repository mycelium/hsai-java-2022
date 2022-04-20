import java.lang.Integer.min
import java.lang.Integer.max
import java.nio.file.Paths
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.collections.MutableMap


val helpKey = "--help"
val iKey = "--i"
val nKey = "-n"
val rKey = "-r"
val excludeKey = "--exclude"
val AKey = "-A"
val BKey = "-B"
val errMap = mapOf("Error" to "bad")
val pathArg = "pathArg"
val regexArg = "regexArg"
val excludeDelimiter = "-meow-"

// Any order of keys allowed, but regex and path MUST be the last arguments

fun main(args: Array<String>){
    val argsDict = parseArgs(args)

    try {
        checkArgs(argsDict)
    }
    catch(e: Exception)
    {
        println(e.message)
        return
    }

    var excludedFiles= listOf<Path>()
    if(argsDict.contains(excludeKey)) {
        excludedFiles = argsDict[excludeKey]!!.split(excludeDelimiter).stream().map(Paths::get).collect(Collectors.toList())
    }

    var A = 0
    var B = 0
    if(argsDict.contains(AKey))
        A=argsDict[AKey]!!.toInt()
    if(argsDict.contains(BKey))
        B=argsDict[BKey]!!.toInt()

    if (argsDict.contains(helpKey))
        printHelp()

    if(argsDict.contains(pathArg) && Files.isRegularFile(Paths.get(argsDict[pathArg]))){
        val regex : Regex = if(argsDict.contains(iKey))
            argsDict[regexArg]!!.toRegex(RegexOption.IGNORE_CASE)
        else
            argsDict[regexArg]!!.toRegex()

        println("Found in file: ${argsDict[pathArg]} on request: ${argsDict[regexArg]}")
        val res = search(Files.readAllLines(Paths.get(argsDict[pathArg]!!)), A, B, argsDict.contains(nKey), regex)
        if (res.isNotEmpty()) println(res.joinToString("\n")+"\n------------------------")
    }
    if(argsDict.contains(pathArg) && Files.isDirectory(Paths.get(argsDict[pathArg]))){
        val regex : Regex = if(argsDict.contains(iKey))
            argsDict[regexArg]!!.toRegex(RegexOption.IGNORE_CASE)
        else
            argsDict[regexArg]!!.toRegex()

        println("Found in location: ${argsDict[pathArg]} on request: ${argsDict[regexArg]}")
        val paths = if (argsDict.contains(rKey)) Files.walk(Paths.get(argsDict[pathArg]!!)).filter(Files::isRegularFile).toList().minus(excludedFiles.toSet()) else Files.list(Paths.get(argsDict[pathArg]!!)).filter(Files::isRegularFile).toList().minus(excludedFiles.toSet())

        for (p in paths) {
            val res = search(Files.readAllLines(p), A, B, argsDict.contains(nKey),regex)
            if (res.isNotEmpty()) println("Found in file: $p \n${res.joinToString("\n")} ")
        }
    }

    println("(This is just a cat, not part of the output. After all, we are worse than grep in all but cats) —ฅ/ᐠ. ̫ .ᐟ\\ฅ —")
}

fun parseArgs(args: Array<String>): MutableMap<String, String> {
    var argMap = mutableMapOf<String, String>()
    var i = 0
    val end = if (args.size < 2) args.size else args.size - 2

    while(i < end){
        when (args[i]) {
            helpKey -> {
                argMap[helpKey] = "1"
                i++
            }
            iKey -> {
                argMap[iKey] = "1"
                i++
            }
            nKey -> {
                argMap[nKey] = "1"
                i++
            }
            rKey -> {
                argMap[rKey] = "1"
                i++
            }
            AKey -> {
                argMap[AKey] = args[i + 1]
                i += 2
            }
            BKey -> {
                argMap[BKey] = args[i + 1]
                i += 2
            }
            excludeKey -> {
                argMap[excludeKey] = args[i + 1]
                i += 2
            }
            else -> {
                return errMap as MutableMap<String, String>
            }
        }
    }
    if (args.size > 1)
    {
        argMap[pathArg] = args[args.size - 1]
        argMap[regexArg] = args[args.size - 2]
    }
    return argMap
}

fun checkArgs(argsDict: MutableMap<String, String>)
{
    if(argsDict.contains("Error")|| argsDict.isEmpty()){
        throw Exception("Unknown key")
    }

    if(argsDict.isEmpty()){
        throw Exception("At least one key required")
    }

    if(argsDict.contains(pathArg))
        if (!Files.exists(Paths.get(argsDict[pathArg]!!))) {
            throw Exception("File or directory not exist")
        }

    if(argsDict.contains(excludeKey)) {
        val excludedFiles = argsDict[excludeKey]!!.split(excludeDelimiter).stream().map(Paths::get).collect(Collectors.toList())
        for (e in excludedFiles)
            if (!Files.exists(e)) {
                throw Exception("File or directory not exist")
            }
    }
}

fun search(line_arr: List<String>, A: Int, B: Int, n: Boolean, regex: Regex): List<String> {
    var res = mutableListOf<String>()
    for (i in line_arr.indices) {
        if (line_arr[i].contains(regex)){
            if (B>0)
                for (j in max(0, i-B) until i)
                {
                    var strNum = if (n) j.toString() + " " else ""
                    res.add(strNum + line_arr[j])
                }
            var strNum = if (n) i.toString() + " " else ""
            res.add("FOUND: " + strNum + line_arr[i])
            if (A>0)
                for (k in i+1..min(line_arr.size-1, i+A))
                {
                    var strNum = if (n) k.toString() + " " else ""
                    res.add(strNum + line_arr[k])
                }
            res.add("----------------------------------")
        }
    }
    return res
}

fun printHelp()
{
    println(
        "Grek utility. We are worse than grep in all but cats /ᐠ-ᆽ-ᐟ \\\n${nKey} <regEx> <pathToSingleFile>: Output line number within a file" +
                "\n${rKey} <regEx> <pathToFolder> : Find in files in folder" +
                "\n${AKey} <Int> : print <Int> lines of trailing context after matching lines" +
                "\n${BKey} <Int> : print <Int> lines of trailing context before matching lines" +
                "\n${excludeKey} \"<pathToSingleFile>[${excludeDelimiter}<pathToSingleFile>[${excludeDelimiter}<pathToSingleFile>...]]\" : Exclude file(s) from search" +
                "\n${helpKey} : Help" +
                "\n${iKey} : Ignore the case" +
                "\n---------------------"
    )
}