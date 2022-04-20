package org.polytech.grek

import org.junit.Test

import org.junit.Assert.*
import java.nio.file.Path

class ValidatorTest {

    @Test
    fun validateTestIncorrectPath() {
        val cmdParams = CmdParams(true, true, 5, 5, true, emptyList(), "TeST", Path.of("src/test/resources/test1.txt"))

        try {
            Validator.validate(cmdParams)
        } catch (e: IllegalArgumentException) {
            assertEquals("Incorrect path!", e.message)
        }
    }

    @Test
    fun validateTestPathIsDirectoryWithoutFlagR() {
        val cmdParams = CmdParams(true, false, 5, 5, true, emptyList(), "TeST", Path.of("src/test/resources/test1"))

        try {
            Validator.validate(cmdParams)
        } catch (e: IllegalArgumentException) {
            assertEquals("This path is a directory! Use -r if you want to find in files in this directory!", e.message)
        }
    }

    @Test
    fun validateTestNegativeNumberA() {
        val cmdParams = CmdParams(true, true, -5, 5, true, emptyList(), "TeST", Path.of("src/test/resources/test1"))

        try {
            Validator.validate(cmdParams)
        } catch (e: IllegalArgumentException) {
            assertEquals("Number for -A must be greater or equal to zero!", e.message)
        }
    }

    @Test
    fun validateTestNegativeNumberB() {
        val cmdParams = CmdParams(true, true, 5, -5, true, emptyList(), "TeST", Path.of("src/test/resources/test.txt"))

        try {
            Validator.validate(cmdParams)
        } catch (e: IllegalArgumentException) {
            assertEquals("Number for -B must be greater or equal to zero!", e.message)
        }
    }
}