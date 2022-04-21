fun main(args: Array<String>) {
    if (args.size < 2) throw Exception("Invalid input")

    val parameters: GrekParams = try {
        GrekParams.create(args)
    } catch (e: Throwable) {
        println("Can't create grek parameters")
        e.printStackTrace()
        return
    }

    try {
        print(Grek(parameters).run())
    } catch (e: Throwable) {
        println("Can't run grek")
        return
    }
}
