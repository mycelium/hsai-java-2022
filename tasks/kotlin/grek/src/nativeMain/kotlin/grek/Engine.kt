package grek

import okio.Path
import kotlin.native.concurrent.Future
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

class Engine(private val regex: Regex) {

    init {
        regex.freeze()
    }

    fun processData(data: Map<Path, Sequence<String>>): Map<Path, Sequence<Int>> {
        return data.mapValues { entry -> locateMatches(entry.value) }.mapValues { entry -> entry.value.result }
    }

    private fun locateMatches(content: Sequence<String>): Future<Sequence<Int>> {
        content.freeze()
        return Worker.start().execute(TransferMode.SAFE, { Pair(content, regex) }, { input ->
            val workerContent = input.first
            val regex = input.second

            workerContent.withIndex().filter { item -> item.value.contains(regex) }.map { item -> item.index }
        })
    }
}