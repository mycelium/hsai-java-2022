fun main(args: Array<String>) {
    try {
        var data = InputData()
        data.setParameters(args)
        Grek(data).find()
    } catch(e: Exception) {
        println(e.message)
    }
}