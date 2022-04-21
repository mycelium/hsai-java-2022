data class GrekParams(
    var regex: Regex = Regex(""),
    var path: String = "",
    var printLines: Boolean = false,
    var recursive: Boolean = false,
    var afterContext: Int = 0,
    var beforeContext: Int = 0
) {

    companion object {
        fun create(args: Array<String>): GrekParams = GrekParams().apply {
            for ((index, value) in args.withIndex()) {
                when (value) {
                    "-A" -> afterContext = args[index + 1].toInt()
                    "-B" -> beforeContext = args[index + 1].toInt()
                    "-n" -> printLines = true
                    "-r" -> recursive = true
                    "-nr" -> {
                        printLines = true
                        recursive = true
                    }
                    "-rn" -> {
                        printLines = true
                        recursive = true
                    }
                }
            }
            regex = args[args.lastIndex - 1].toRegex()
            path = args[args.lastIndex]
        }
    }

}
