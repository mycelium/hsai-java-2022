import arguments.*

fun main(args: Array<String>) {
    exceptions.handleErrors(args)

    val arguments: Args = fillArgs(args)

    try {
        println(Process().launch(arguments))
    } catch (e: Exception) {
        if (arguments.help){
            //
        } else {
            println("Error occured: " + e.message)
        }
    }
}
