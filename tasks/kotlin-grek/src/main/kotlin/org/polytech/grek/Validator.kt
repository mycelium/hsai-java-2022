package org.polytech.grek

import java.lang.IllegalArgumentException
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

object Validator {

    fun validate(app: CmdParams) {
        when {
            !app.path.exists() -> throw IllegalArgumentException("Incorrect path!")
            !app.r && app.path.isDirectory() -> throw IllegalArgumentException("This path is a directory! Use -r if you want to find in files in this directory!")
            app.a < 0 -> throw IllegalArgumentException("Number for -A must be greater or equal to zero!")
            app.b < 0 -> throw IllegalArgumentException("Number for -B must be greater or equal to zero!")
        }
    }
}
