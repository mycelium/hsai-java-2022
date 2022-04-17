import org.junit.jupiter.api.Test

internal class GrekTest {
    @Test
    fun RunTest() {
        val paramsgreek: Array<String> =
            listOf("greek", "-B", "1", "-A", "5", "-n", """\d*\+\d*=\d*""", "src/test/resources/1").toTypedArray()
        val grekTest: Grek = Grek(paramsgreek)
        grekTest.run()
    }

    @Test
    fun DeepRunTest() {
        val paramsgreek: Array<String> =
            listOf("greek", "-B", "1", "-A", "5", "-nr", """\d*\+\d*=\d*""", "src/test/resources/1").toTypedArray()
        val grekTest: Grek = Grek(paramsgreek)
        grekTest.run()
    }

    @Test
    fun nSingleFileTest() {
        val paramsgreek: Array<String> =
            listOf("greek", "-A", "5", "-n", """\d*\+\d*=\d*""", "src/test/resources/a.txt").toTypedArray()
        val grekTest: Grek = Grek(paramsgreek)
        grekTest.run()
    }

    @Test
    fun HelpTest() {
        val paramsgreek: Array<String> =
            listOf("greek", "--help").toTypedArray()
        val grekTest = Grek(paramsgreek)
        grekTest.run()
    }

    @Test
    fun ITest() {
        val paramsgreek: Array<String> =
            listOf("greek", "-i", "FiLe.*", "src/test/resources/1/1_1/ff.txt").toTypedArray()
        val grekTest: Grek = Grek(paramsgreek)
        grekTest.run()
    }

    @Test
    fun ExcludeTest() {
        val paramsgreek: Array<String> =
            listOf("greek", "-nr",  "--exclude", "src/test/resources/1/f.txt" ,"""\d*\+\d*=\d*""", "src/test/resources/1").toTypedArray()
        val grekTest: Grek = Grek(paramsgreek)
        grekTest.run()
    }
}