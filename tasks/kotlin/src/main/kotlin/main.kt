import com.xenomachina.argparser.ArgParser

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::CommandsHandler)
        .hand()
        .forEach {
            it.forEach { println(it) }
        }
}
