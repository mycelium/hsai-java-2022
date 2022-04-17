import org.junit.Assert
import org.junit.jupiter.api.Test

internal class GrekTest {
    @Test
    fun Test1() {
        var params: Array<String> =
                listOf("grek", "-n", "[a-zA-Z]{10}", "src/test/resources").toTypedArray()
        var expected = "src/test/resources/test.txt\n5: categories\n8: Barbecutie\n"
        Assert.assertEquals(expected, Grek().run(createParams(params)))
    }

    @Test
    fun Test2() {
        var params: Array<String> =
                listOf("grek", "-nr", "^[a-zA-Z]*[0-9]+\$", "src/test/resources").toTypedArray()
        val expected="src/test/resources/test2.txt\n10: gsdFDHD424\nsrc/test/resources/test.txt\n14: asdADGADS213\n"
        Assert.assertEquals(expected, Grek().run(createParams(params)))
    }
}