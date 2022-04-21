import org.junit.Assert
import org.junit.jupiter.api.Test

class GrekTest {
    @Test
    fun Test1() {
        var params: Array<String> =
                listOf("grek", "-n", "[a-zA-Z]{10}", "src/test/resources").toTypedArray()
        var expected = "src/test/resources/test.txt\n5: momsAAmoms\n14: DadDadDadD\n"
        Assert.assertEquals(expected, Grek(createParams(params)).run())
    }

    @Test
    fun Test2() {
        var params: Array<String> =
                listOf("grek", "-nr", "^[a-zA-Z]*[0-9]+\$", "src/test/resources").toTypedArray()
        val expected="src/test/resources/test2.txt\n13: catCat2\nsrc/test/resources/test.txt\n17: catCat2\n"
        Assert.assertEquals(expected, Grek(createParams(params)).run())
    }
}