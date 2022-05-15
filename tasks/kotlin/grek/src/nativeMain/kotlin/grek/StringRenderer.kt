package grek

import okio.Path
import kotlin.math.max
import kotlin.math.min
import kotlin.native.concurrent.Future
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

class StringRenderer {

    fun render(
        matches: Map<Path, Sequence<Int>>,
        data: Map<Path, Sequence<String>>,
        linesBefore: Int,
        linesAfter: Int,
        showLines: Boolean
    ): String {
        val matchBlocks =
            matches.mapValues { entry -> preprocessMatches(entry.value.toList(), linesBefore + linesAfter) }
                .mapValues { it.value.result }

        val blockStrings = mutableListOf<String>()
        matchBlocks.forEach { file ->
            if (data[file.key] != null) {
                val fileData = data[file.key]?.toList()
                file.value.forEach { block ->
                    val blockBuilder = StringBuilder()
                    for (line in max(0, block.first() - linesBefore)..min(
                        ((fileData?.size) ?: 0) - 1,
                        block.last() + linesAfter
                    )) {
                        if (block.contains(line))
                            blockBuilder.appendLine("${file.key}${if (showLines) ":$line" else ""}:${fileData?.get(line)}")
                        else
                            blockBuilder.appendLine("${file.key}${if (showLines) "-$line" else ""}-${fileData?.get(line)}")
                    }
                    blockStrings.add(blockBuilder.toString())
                }
            }
        }
        return blockStrings.joinToString(separator = "--\n")
    }

    private fun preprocessMatches(matches: List<Int>, gap: Int): Future<List<List<Int>>> {
        return Worker.start().execute(TransferMode.SAFE, { Pair(matches.freeze(), gap.freeze()) }, {
            val localMatches = it.first
            val localGap = it.second
            val matchGroups = mutableListOf<MutableList<Int>>()
            if (localMatches.isNotEmpty()) {
                matchGroups.add(mutableListOf(localMatches.first()))
                val furtherMatches = localMatches.toList().filterIndexed { index, _ -> index > 0 }
                for (i in furtherMatches) {
                    if (i - matchGroups.last().last() <= localGap) {
                        matchGroups.last().add(i)
                    } else matchGroups.add(mutableListOf(i))
                }
            }
            matchGroups.freeze()
        })
    }
}