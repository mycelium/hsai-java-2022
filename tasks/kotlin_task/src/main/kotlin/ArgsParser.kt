import kotlin.system.exitProcess

fun parseArgs(args: Array<String>, settings: Arguments) {
    for (curr in args.indices) {
        if (args[curr].contains("--help")) {
            showHelp()
            exitProcess(1)
        }
        if (args[curr].contains("-i")) {
            settings.registerIgnore = true
            continue
        }
        if (args[curr].contains("--exclude")) {
            val files = args[curr].substring(args[curr].indexOf('=') + 1).split("|")
            settings.excludeList.addAll(files)
            continue
        }
        if (args[curr].contains("-nr")) {
            settings.directoryCheck = true
            continue
        }
        if (args[curr].contains("-n")) {
            settings.fileCheck = true
            continue
        }
        try{
            if (args[curr].contains("-B")) {
                settings.beforeRows = args[curr + 1].toInt()
                continue
            }
            if (args[curr].contains("-A")) {
                settings.afterRows = args[curr + 1].toInt()
                continue
            }
        }
        catch (e: Exception){
            println("Wrong row count")
        }
    }
    settings.pattern = args[args.size - 2]
    settings.path = args[args.size - 1]
}

private fun showHelp() {
    println("Example : -nr -i -A 2 -B 3 --exclude=data1|data2 wew C:\\Users\\Forcause\\Downloads\\srcK\\ru\\testData")
    println("Keys: -n - single file\n-nr - directory\n-An - rows after (n - number of rows)\n-Bn - before rows (n - number of rows)")
    println("-i - ignore case\n--exclude - files to exclude in directory search\n--help - get help")
    println("Last two elements must be regex pattern and path")
}