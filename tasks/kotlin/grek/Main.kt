fun main(args: Array<String>) {
    if (args.size < 2) throw Exception("Incorrect input")

    val params: Parameters = try { Parameters.build(args) } catch (e: Throwable) {
        println("Cannot create parameters")
        e.printStackTrace()
        return
    }

    try { println(GrekClass(params).execute()) } catch (e: Throwable) {
        println("Cannot execute")
        e.printStackTrace()
    }
}
