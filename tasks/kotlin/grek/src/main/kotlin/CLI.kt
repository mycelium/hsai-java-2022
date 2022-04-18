
object CLI {

    fun readCLI(args: Array<String>) {
        if (args.isEmpty() || args.contains("--help")) {
            println("\n-n <regular expression> <path_to_single_file>: search through file" +
                    "\n-nr <regular expression> <path_to_folder>: search through files in folder" +
                    "\n-B <value>: strings before" +
                    "\n-A <value>: strings after" +
                    "\n-i: case insensitive" +
                    "\n--exclude \"<path_to_single_file>[|<path_to_single_file>...]\": exclude file(s) out of -nr ")
            flags["help"] = true
        } else {
            if (args.contains("-n")) {
                flags["file"] = true
                val idx = args.indexOf("-n")
                values["regex"] = args[idx + 1]
                values["path"] = args[idx + 2]
            } else if (args.contains("-nr")) {
                val idx = args.indexOf("-nr")
                values["regex"] = args[idx + 1]
                values["path"] = args[idx + 2]
                if (args.contains("--exclude")) {
                    flags["exclude"] = true
                    values["exclude"] = args[args.indexOf("--exclude") + 1]
                }
            }
            if (args.contains("-B")) {
                flags["before"] = true
                values["before"] = args[args.indexOf("-B") + 1]
            }
            if (args.contains("-A")) {
                flags["after"] = true
                values["after"] = args[args.indexOf("-A") + 1]
            }
            if (args.contains("-i")) {
                flags["case"] = true
            }
        }
    }

    fun printResponse(response: List<String>, fpath: String) {
        println("File $fpath:\n")
        response.forEach { line -> println(line) }
        println("\n----------------")
    }
}