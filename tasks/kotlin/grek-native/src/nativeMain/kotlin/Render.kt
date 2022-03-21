package grek

import arrow.core.split


/**
 * Render grek output to console
 */
suspend fun <R> R.renderGrek(file: ProcessedFile, printFileName: Boolean) where R : Reader<Options> =
    // Do only if seq not null
    file.matchingLines.lines.split()?.let {
        val lines = sequenceOf(it.second) + it.first

        // Print file name when processing folder
        if (printFileName) println(file.filePath)

        for (line in lines) {
            println(this.ask.renderOpts.renderLine(line))
        }
    }
