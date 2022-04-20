import kotlinx.cli.*

fun main(args: Array<String>) {
    for (s: String in Searcher(ArgumentSet(args)).searchRecursive()) {
        System.out.println(s)
    }
}