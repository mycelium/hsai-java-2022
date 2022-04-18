import kotlin.system.exitProcess

fun parseArgs(args: Array<String>, settings: Arguments) {
    for (arg in args) {
        if (arg.contains("--help")) {
            showHelp()
            exitProcess(1)
        }
        if (arg.contains("-i")) {
            settings.registerIgnore = true
            continue
        }
        if (arg.contains("--exclude")) {
            val files = arg.substring(arg.indexOf('=') + 1).split("|")
            settings.excludeList.addAll(files)
            continue
        }
        if (arg.contains("-nr")) {
            settings.directoryCheck = true
            continue
        }
        if (arg.contains("-n")) {
            settings.fileCheck = true
            continue
        }
        if (arg.contains("-B")) {
            settings.beforeRows = arg.substring(arg.indexOf('B') + 1).toInt()
            continue
        }
        if (arg.contains("-A")) {
            settings.afterRows = arg.substring(arg.indexOf('A') + 1).toInt()
            continue
        }
    }
    settings.pattern = args[args.size - 2]
    settings.path = args[args.size - 1]
}

private fun showHelp() {
    println("Example : -nr -i -A2 -B3 --exclude=data1|data2 wew \\testData")
    println("Keys: -n - single file\n-nr - directory\n-An - rows after (n - number of rows)\n-Bn - before rows (n - number of rows)")
    println("-i - ignore case\n--exclude - files to exclude in directory search\n--help - get help")
    println("Last two elements must be regex pattern and path")
}
