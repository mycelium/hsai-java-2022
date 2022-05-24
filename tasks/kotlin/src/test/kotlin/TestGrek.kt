import kotlin.test.Test
import kotlin.test.assertEquals
import createCommand

internal class TestGrek {

    @Test
    fun TestIgnoreCase() {
        val input = "grek -n -i rectangle src\\test\\resources"
        val args: List<String> = input!!.split(" ")
        val expected = "src\\test\\resources\\test.txt\n" +
                "2: rectangle\n"
        assertEquals(expected, CommandLine(createCommand(args)).run())
    }

    @Test
    fun TestContext() {
        val input = "grek -A 4 -i GEMOGLOBIN src\\test\\resources"
        val args: List<String> = input!!.split(" ")
        val expected = "src\\test\\resources\\test.txt\n" +
                "gemoglobin\n" +
                "GEMOGLOBIN\n" +
                "12-12-12-456\n" +
                "\n" +
                "hsdjfs772\n"
        assertEquals(expected, CommandLine(createCommand(args)).run())
    }
}