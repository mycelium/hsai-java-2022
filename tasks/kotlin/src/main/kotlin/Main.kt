fun main(args: Array<String>) {
    val booleans = Booleans()
    val info = Info()
    val deepWalker = DeepWalker()
    val handler = Handler(deepWalker)
    handler.assign(args, booleans, info)
    if (booleans.checkFile) handler.processFile(booleans, info, deepWalker)
    else handler.processDirectory(booleans, info, deepWalker)
}