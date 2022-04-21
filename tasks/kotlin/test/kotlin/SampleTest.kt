import kotlin.test.Test
import kotlin.test.assertEquals
import arguments.fillArgs

internal class SampleTest {

    @Test
    fun TestIgnoreCase() {
        val input = "-i word src\\test\\resources\\f4.txt"
        val args: Array<String> = input.split(" ").toTypedArray()
        val expected = "Word \t \n"
        assertEquals(expected, Process().launch(fillArgs(args)))
    }

    @Test
    fun TestContext() {
        val input = "-n -A 1 -i wo src\\test\\resources\\f4.txt"
        val args: Array<String> = input.split(" ").toTypedArray()
        val expected = "1) Word \t2) world \t \n" +
                "2) world \t3) worm \t \n" +
                "3) worm \t \n"
        assertEquals(expected, Process().launch(fillArgs(args)))
    }
}
