fun main(args: Array<String>) {
    val options: GrekOptions
    try {
        options = CmdOptions().getOptions(args)
        print(Grek(options).run())
    }
    catch (e: Exception) {
        println(e.message)
    }
}


