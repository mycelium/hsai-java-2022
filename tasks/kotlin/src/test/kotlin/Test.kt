import org.junit.Test
import kotlin.test.assertEquals

internal class Test {

    @Test
    fun testWithIgnoreCase() {
        val input = "-i word src\\test\\resources\\input2.txt"
        val args: Array<String> = input.split(" ").toTypedArray()
        val expected = "Word \t \n"
        val actual = Handler().launch(fillArgs(args))
        println("expected: $expected \n" +
                "actual: $actual")
        assertEquals(expected, actual)
    }

    @Test
    fun testContext() {
        val input = "-n -A 1 -i wo src\\test\\resources\\input2.txt"
        val args: Array<String> = input.split(" ").toTypedArray()
        val expected = "1) Word \t2) world \t \n" +
                "2) world \t3) worm \t \n" +
                "3) worm \t \n"
        val actual = Handler().launch(fillArgs(args))
        println("expected: $expected \n" +
                "actual: $actual")
        assertEquals(expected, actual)
        assertEquals(expected, actual)
    }
}
