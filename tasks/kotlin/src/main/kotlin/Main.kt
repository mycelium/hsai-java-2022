fun main() {
    //C:\Users\acer\testtest
    //grek -n -i CATS C:\Users\acer\testtest
    print("> ")
    val str = readLine()
    val arr: List<String> = str!!.split(" ")
    if (arr.size < 2) {
        throw Exception("Invalid input!")
    }
    val params: GrekCommand
    try {
        params = createCommand(arr)
    } catch (ex: Throwable) {
        print ("Can't create grek parameters. ${ex.message}")
        return
    }
    try {
        print(CommandLine(params).run())
    } catch (ex: Throwable) {
        println("Can't run grek. ${ex.message}")
        return
    }
}
