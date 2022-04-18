val flags: MutableMap<String, Boolean> = mutableMapOf(
    "help" to false,
    "file" to false,
    "before" to false,
    "after" to false,
    "case" to false,
    "exclude" to false)

val values: MutableMap<String, String> = mutableMapOf(
    "path" to "",
    "regex" to "",
    "before" to "0",
    "after" to "0",
    "exclude" to "")

fun main(args: Array<String>) {
    CLI.readCLI(args)
    Processor.process()
}
