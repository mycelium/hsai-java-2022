import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class grekTest {

    @Test
    fun parseArgsTest() {
        val args = arrayOf("-r", "-A", "7", "-B", "5", "--exclude", "folder/test.txt-meow-folder/test2.txt", " [а-я]{5} ", "folder" )
        val testArgs = parseArgs(args)
        val checkArgs = mutableMapOf<String, String>()
        checkArgs[rKey] = "1"
        checkArgs[AKey] = "7"
        checkArgs[BKey] = "5"
        checkArgs[excludeKey] = "data/test.txt-meow-data/test2.txt"
        checkArgs[pathArg] = "data"
        checkArgs[regexArg] = " [а-я]{5} "

        assertEquals(checkArgs[rKey], testArgs[rKey])
    }

    @Test
    fun checkArgsTest() {
        val args = arrayOf("-r", "Secret Note", "kotlin_test" )
        val testArgs = parseArgs(args)

        checkArgs(testArgs)
    }

    @Test
    fun searchTest() {
        val lines = listOf("Hello!", "I am", "a test list", "I pretend to be", "lines of file(s) :)")
        val testRes = search(lines, 1, 1, false, "test".toRegex())
        val expectedRes = listOf("I am", "FOUND: a test list", "I pretend to be", "----------------------------------")

        assertEquals(expectedRes, testRes)
    }

    @Test
    fun mainTest1() {
        println("\nMain test 1\n")
        val args = arrayOf("-r", "Dinosaur Egg", "kotlin_test" )
        main(args)
    }
    @Test
    fun mainTest2() {
        println("\nMain test 2\n")
        val args = arrayOf("Dinosaur Egg", "kotlin_test" )
        main(args)
    }

    @Test
    fun mainTest3() {
        println("\nMain test 3\n")
        val args = arrayOf("-r", "-n", "-B", "1", "-A", "1", "Dinosaur Egg", "kotlin_test" )
        main(args)
    }

}