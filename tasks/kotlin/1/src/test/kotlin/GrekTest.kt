import org.junit.jupiter.api.Test

class GrekTest {
    fun testRun(args: Array<String>) {
        var data = InputData()
        data.setParameters(args)
        Grek(data).find()
        println("\n")
    }

    @Test
    fun basicTest() {
        println("grek никому src/test/resources/text.txt")
        val args = arrayOf("grek", "никому", "src/test/resources/text.txt")
        testRun(args)
    }

    @Test
    fun numerateTest() {
        println("grek -n компромисс src/test/resources/2/text.txt")
        val args = arrayOf("grek","-n", "компромисс", "src/test/resources/2/text.txt")
        testRun(args)
    }

    @Test
    fun recursiveTest() {
        println("grek -r жизн[ьи] src/test/resources")
        val args = arrayOf("grek","-r", "жизн[ьи]", "src/test/resources")
        testRun(args)
    }

    @Test
    fun numerateAndRecursiveTest() {
        println("grek -nr жизн[ьи] src/test/resources")
        val args = arrayOf("grek","-nr", "жизн[ьи]", "src/test/resources")
        testRun(args)
    }

    @Test
    fun ignoreTest() {
        println("grek -ir (кУПЛЕТ)|(пРИПЕВ) src/test/resources")
        val args = arrayOf("grek","-ir", "(кУПЛЕТ)|(пРИПЕВ)", "src/test/resources")
        testRun(args)
    }

    @Test
    fun beforeAndAfterTest1() {
        println("grek -i -r -n -A 2 -B 3 \"куплет 2\" src/test/resources")
        val args = arrayOf("grek","-i", "-r", "-n", "-A", "2", "-B", "3", "куплет 2", "src/test/resources")
        testRun(args)
    }

    @Test
    fun beforeAndAfterTest2() {
        println("grek -in -A 2 -B 1 \"я\" src/test/resources/text.txt")
        val args = arrayOf("grek","-in", "-A", "2", "-B", "1", "я", "src/test/resources/text.txt")
        testRun(args)
    }

    @Test
    fun excludeTest() {
        println("grek -ir --exclude [ht]ext.txt \"куплет 2\" src/test/resources")
        val args = arrayOf("grek","-ir", "--exclude", "[ht]ext.txt", "куплет", "src/test/resources")
        testRun(args)
    }

    @Test
    fun helpTest() {
        println("grek --help")
        val args = arrayOf("grek","--help")
        testRun(args)
    }
}