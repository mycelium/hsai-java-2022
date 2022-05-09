import org.junit.Test
import kotlin.test.assertEquals

class GrekTest {

    @Test
    fun noOptionsTest() {
        val args = arrayOf("designed", "src/test/resources/file1.txt")
        val options = CmdOptions().getOptions(args)
        assertEquals(
            "with type inference. Kotlin is designed to interoperate fully with Java, and the\n",
            Grek(options).run()
        )
    }

    @Test
    fun numberedLinesTest() {
        val args = arrayOf("-n", "compile", "src/test/resources/file1.txt")
        val options = CmdOptions().getOptions(args)
        assertEquals(
            "5: compiles to JavaScript (e.g., for frontend web applications using React[6]) or native code\n" +
                    "11: Kotlin has been included as an alternative to the standard Java compiler.\n" +
                    "12: The Android Kotlin compiler produces Java 8 bytecode by default (which runs in any later JVM),\n",
            Grek(options).run()
        )
    }

    @Test
    fun recursiveSearchTest() {
        val args = arrayOf("-nr", "JVM", "src/test/resources")
        val options = CmdOptions().getOptions(args)
        assertEquals(
            "src\\test\\resources\\dir1\\file2.txt\n" +
                    "6: compiled to bytecode that can run on any Java virtual machine (JVM) regardless of\n" +
                    "src\\test\\resources\\file1.txt\n" +
                    "3: JVM version of Kotlin's standard library depends on the Java Class Library but type\n" +
                    "4: inference allows its syntax to be more concise. Kotlin mainly targets the JVM, but also\n" +
                    "12: The Android Kotlin compiler produces Java 8 bytecode by default (which runs in any later JVM),\n" +
                    "14: has bidirectional record class interoperability support for JVM, introduced in Java 16, considered stable\n",
            Grek(options).run()
        )
    }

    @Test
    fun ignoreCaseTest() {
        val args = arrayOf("-nr", "-i", "github", "src/test/resources")
        val options = CmdOptions().getOptions(args)
        assertEquals(
            "src\\test\\resources\\dir1\\file2.txt\n" +
                    "11: Java was one of the most popular programming languages in use according to GitHub,\n",
            Grek(options).run()
        )
    }

    @Test
    fun showContextTest() {
        val args = arrayOf("-n", "-B", "1", "-A", "2", "virtual", "src/test/resources/dir1/file2.txt")
        val options = CmdOptions().getOptions(args)
        assertEquals(
            "5: that support Java without the need to recompile. Java applications are typically\n" +
                    "6: compiled to bytecode that can run on any Java virtual machine (JVM) regardless of\n" +
                    "7: the underlying computer architecture. The syntax of Java is similar to C and C++,\n" +
                    "8: but has fewer low-level facilities than either of them. The Java runtime provides\n" +
                    "15: as a core component of Sun Microsystems' Java platform. The original and reference implementation\n" +
                    "16: Java compilers, virtual machines, and class libraries were originally released by Sun under proprietary licenses.\n" +
                    "17: As of May 2007, in compliance with the specifications of the Java Community Process,\n",
            Grek(options).run()
        )
    }

    @Test
    fun excludeTest() {
        val args = arrayOf("-nr", "-A", "1", "--exclude", "1.txt", "develop", "src/test/resources/dir1/file2.txt")
        val options = CmdOptions().getOptions(args)
        assertEquals(
            "12: particularly for client–server web applications, with a reported 9 million developers.\n" +
                    "13: \n" +
                    "14: Java was originally developed by James Gosling at Sun Microsystems and released in May 1995\n" +
                    "15: as a core component of Sun Microsystems' Java platform. The original and reference implementation\n",
            Grek(options).run()
        )
    }

    @Test
    fun helpTest() {
        val args = arrayOf("--help")
        val options = CmdOptions().getOptions(args)
        assertEquals(
            "grek [-n][-r] [-i] [-A NUM] [-B NUM] [--exclude]\n" +
                    "-n - print number of lines\n" +
                    "-r - recursive search\n" +
                    "-i - ignore case\n" +
                    "-A NUM - print NUM lines of trailing context after  matching  line\n" +
                    "-B NUM - print NUM lines of leading context before  matching  line\n" +
                    "--exclude <regex> - skip file if its path matches given regex",
            Grek(options).run()
        )
    }
}