fun parseArgs(args: Array<String>, settings: Arguments) {
    for (arg in args) {
        if (arg.contains("--help")) {
            showHelp()
            break
        }
        if (arg.contains("-i")) {
            settings.registerIgnore = true
            continue
        }
        if (arg.contains("--exclude")) {
            val files = arg.substring(arg.indexOf('=') + 1).split("|")
            settings.excludeList.addAll(files)
            break
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
    TODO("Not yet implemented")
}
