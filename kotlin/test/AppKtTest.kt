import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

internal class AppKtTest {
    @Test
    fun nrOption() {
        val args = arrayOf("-n", "Сильные", "resources/text1.txt")
        val dict = grekArgs(args)
        val a = 0
        val b = 0

        val regex = dict["regexN"]!!.toRegex()
        val result = grekFind(Files.readAllLines(Paths.get(dict["-n"]!!)), a, b, regex)
        val expected = Arrays.asList("Found in str1: Сильные умы никогда не бывают послушными.")

        assertEquals(expected, result)
    }

    @Test
    fun strAfterOption() {
        val args = arrayOf("-n", "Сильные", "resources/text1.txt", "-A", "1")
        val dict = grekArgs(args)
        val a = dict["-A"]?.toInt()
        val b = 0

        val regex = dict["regexN"]!!.toRegex()
        val result = a?.let { grekFind(Files.readAllLines(Paths.get(dict["-n"]!!)), it, b, regex) }
        val expected = Arrays.asList("Found in str1: Сильные умы никогда не бывают послушными., Джек Лондон.")

        assertEquals(expected, result)
    }
}