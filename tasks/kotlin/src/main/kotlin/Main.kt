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
        print(Grek().run(params))
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
            "-A" -> params.afterContext = args[index + 1].toInt()
            "-B" -> params.beforeContext = args[index + 1].toInt()
        }
    }
    params.regex = args[args.lastIndex - 1].toRegex()
    params.path = args[args.lastIndex]
    return params
}