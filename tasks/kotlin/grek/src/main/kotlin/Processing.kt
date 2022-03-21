package grek


/**
 * Process file (or any List<String>) contents, returning Sequence of processed lines
 */
fun <R> R.processContents(contents: List<String>): MatchingLines where R : HasOptions {
    val ixContents = contents.withIndex().toList()
    val processed = ixContents.asSequence()
        // Find lines matching regex
        .filter { this.regexOpt.containsMatchIn(it.value) }
        // Get windows around matched lines and flatten
        .flatMap { (matchedLineIx) ->
            // Get window before/after that line
            val (windowCenterIx, window) = ixContents.getWindowAt(matchedLineIx, this.windowOpts)
            // Mark matched line in window
            window.asSequence().map { (lineIx, line) ->
                Line(lineIx + 1, line, lineIx == windowCenterIx)
            }
        }
        // Merge interweaving lines
        .groupingBy { line -> IndexedValue(line.index, line.value) }
        // Select lines that match regex when possible
        .reduce { _, selected, element ->
            if (element.matched)
                element
            else
                selected
        }
        // Guaranteed order as in original sequence
        .values.asSequence()

    return MatchingLines(processed)
}

fun <R> R.processFileContents(fileName: String, contents: List<String>): ProcessedFile where R : HasOptions =
    ProcessedFile(fileName, processContents(contents))