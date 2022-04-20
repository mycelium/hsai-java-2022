package org.polytech.grek

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import org.junit.Test

import org.junit.Assert.*
import java.nio.file.Path

class GrekTest {

    private val absolutePath = Path.of("").toAbsolutePath().toString()

    @Test
    fun findAllTestFlagN() {
        val cmdParams = CmdParams(true, true, 0, 0, true, emptyList(), "TeST", Path.of("src/test/resources/test.txt"))

        val expected = "$absolutePath/src/test/resources/test.txt:1:TEST\n" +
                "$absolutePath/src/test/resources/test.txt:17:TEST\n"

        val output = tapSystemOut {
            Grek(cmdParams).findAll()
        }

        assertEquals(expected, output)
    }

    @Test
    fun findAllTestWithoutFlagI() {
        val cmdParams = CmdParams(false, true, 0, 0, true, emptyList(), "TeST", Path.of("src/test/resources/test.txt"))

        val expected =
                "$absolutePath/src/test/resources/test.txt:TEST\n" +
                "$absolutePath/src/test/resources/test.txt:TEST\n"

        val output = tapSystemOut {
            Grek(cmdParams).findAll()
        }

        assertEquals(expected, output)
    }

    @Test
    fun findAllTestWithoutFlagN() {
        val cmdParams = CmdParams(false, true, 0, 0, false, emptyList(), "TeST", Path.of("src/test/resources/test.txt"))

        val expected = ""

        val output = tapSystemOut {
            Grek(cmdParams).findAll()
        }

        assertEquals(expected, output)
    }

    @Test
    fun findAllTestFlagR() {
        val cmdParams = CmdParams(true, true, 0, 0, true, emptyList(), "TeST", Path.of("src/test/resources"))

        val expected =
                "$absolutePath/src/test/resources/test1/test.txt:1:TEST\n" +
                "$absolutePath/src/test/resources/test1/test.txt:17:TEST\n" +
                "$absolutePath/src/test/resources/test.txt:1:TEST\n" +
                "$absolutePath/src/test/resources/test.txt:17:TEST\n"

        val output = tapSystemOut {
            Grek(cmdParams).findAll()
        }

        assertEquals(expected, output)
    }



    @Test
    fun findAllTestFlagsAB() {
        val cmdParams = CmdParams(true, true, 5, 5, true, emptyList(), "TeST", Path.of("src/test/resources/test.txt"))

        val expected =
                "$absolutePath/src/test/resources/test.txt-0-lol kek\n" +
                "$absolutePath/src/test/resources/test.txt:1:TEST\n" +
                "$absolutePath/src/test/resources/test.txt-2-sdfsdfs\n" +
                "$absolutePath/src/test/resources/test.txt-3-sdfsdf\n" +
                "$absolutePath/src/test/resources/test.txt-4-sdf\n" +
                "$absolutePath/src/test/resources/test.txt-5-sdf\n" +
                "$absolutePath/src/test/resources/test.txt-6-sd\n" +
                "$absolutePath/src/test/resources/test.txt-12-fs\n" +
                "$absolutePath/src/test/resources/test.txt-13-f\n" +
                "$absolutePath/src/test/resources/test.txt-14-sd\n" +
                "$absolutePath/src/test/resources/test.txt-15-fsdf\n" +
                "$absolutePath/src/test/resources/test.txt-16-ssdf\n" +
                "$absolutePath/src/test/resources/test.txt:17:TEST\n" +
                "$absolutePath/src/test/resources/test.txt-18-asd\n" +
                "$absolutePath/src/test/resources/test.txt-19-ads\n"

        val output = tapSystemOut {
            Grek(cmdParams).findAll()
        }

        assertEquals(expected, output)
    }
}
