import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SearcherTest {

    @Test
    fun numTest() {
        val searcher = Searcher(ArgumentSet("-n a dummy".split(" ").toTypedArray()))
        val lines = listOf("one", "two", "three", "quadrillion")
        val expected = listOf("4:quadrillion")
        assert(searcher.search(lines) == expected)
    }

    @Test
    fun contextTest() {
        val searcher = Searcher(ArgumentSet("-n -B 2 -A 1 a dummy".split(" ").toTypedArray()))
        val lines = listOf("one", "a", "", "", "a", "three", "b", "c", "d", "quadrillion")
        val expected = listOf("1:one", "2:a", "3:", "4:", "5:a", "6:three", "8:c", "9:d", "10:quadrillion")
        assert(searcher.search(lines) == expected)
    }

    @Test
    fun ignoreCaseTest() {
        val searcher = Searcher(ArgumentSet("-i a dummy".split(" ").toTypedArray()))
        val lines = listOf("a", "A")
        val expected = listOf("a", "A")
        assert(searcher.search(lines) == expected)
    }

    @Test
    fun regexTest() {
        val searcher = Searcher(ArgumentSet("-i \\d+ dummy".split(" ").toTypedArray()))
        val lines = listOf("a", "A", "\\d", "12", "1", "2342342")
        val expected = listOf("12", "1", "2342342")
        assert(searcher.search(lines) == expected)
    }
}