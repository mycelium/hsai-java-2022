data class Parameters(
    var regex: Regex = Regex(""),
    var filename: String = "",
    var shouldLinesBePrinted: Boolean = false,
    var isRecursive: Boolean = false,
    var afterCtx: Int = 0,
    var beforeCtx: Int = 0
) {

    companion object {
        fun build(args: Array<String>): Parameters = Parameters().apply {
            for ((index, value) in args.withIndex()) {
                when (value) {
                    "-A" -> afterCtx = args[index + 1].toInt()
                    "-B" -> beforeCtx = args[index + 1].toInt()
                    "-n" -> shouldLinesBePrinted = true
                    "-r" -> isRecursive = true
                    "-nr" -> {
                        shouldLinesBePrinted = true
                        isRecursive = true
                    }
                    "-rn" -> {
                        shouldLinesBePrinted = true
                        isRecursive = true
                    }
                }
            }
            regex = args[args.lastIndex - 1].toRegex()
            filename = args[args.lastIndex]
        }
    }

}
