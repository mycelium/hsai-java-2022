fun main(args: Array<String>) {
    for (s: String in Searcher(ArgumentSet(args)).searchRecursive()) {
        println(s)
    }
}