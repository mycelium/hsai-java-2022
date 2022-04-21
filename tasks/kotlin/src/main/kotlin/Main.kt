fun main(args: Array<String>) {
    if (args.size < 2) {
        throw Exception("Invalid input")
    }
    val params: GrekParams
    try {
        params = createParams(args)
    } catch (ex: Throwable) {
        print ("Can't create grek parameters. ${ex.message}")
        return
    }
    try {
        print(Grek(params).run())
    } catch (ex: Throwable) {
        println("Can't run grek. ${ex.message}")
        return
    }
}

fun createParams(args: Array<String>): GrekParams {
    val params = GrekParams()
    for ((index, value) in args.withIndex()) {
        when (value) {
            "-n" -> params.printLines = true
            "-r" -> params.recursive = true
            "-nr" -> {
                params.printLines = true
                params.recursive = true
            }
            "-rn" -> {
                params.printLines = true
                params.recursive = true
            }
            "-A" -> params.after = args[index + 1].toInt()
            "-B" -> params.before = args[index + 1].toInt()
            "--help" -> {
                params.help = true
                print("grek [-n][-r] [-A] [-B] [--help]\n" +
                        "-n - print lines\n" +
                        "-r - recursive walk\n" +
                        "-A - lines of trailing context after each find regex\n" +
                        "-B - lines of leading context before each find regex\n" +
                        "--help - print help")
            }
        }
    }
    params.regex = args[args.lastIndex - 1].toRegex()
    params.path = args[args.lastIndex]
    return params
}