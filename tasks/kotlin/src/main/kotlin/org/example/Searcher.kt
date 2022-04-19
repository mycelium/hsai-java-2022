package org.example

import java.io.File
import kotlin.math.max
import kotlin.math.min

class Searcher(val grek: Grek) {

    fun search() {
        val regex: Regex = if (grek.i) grek.regex.toRegex(RegexOption.IGNORE_CASE) else grek.regex.toRegex()
        val files: List<File> = if (grek.r) {
            grek.path.toFile().walk().filter { it.isFile }.filter { !grek.exclude.contains(it.name) }.toList()
        } else {
            listOf(grek.path.toFile()).filter { !grek.exclude.contains((it.name)) }
        }

        files.forEach {
            regexSearch(it, regex)
        }
    }

    private fun regexSearch(file: File, regex: Regex) {
        val lines = file.readLines()
        for(i in lines.indices){
            if (lines[i].contains(regex)){
                if(grek.n){
                    for(j in max(i-grek.b, 0) until min(lines.count(),i+grek.a+1)){
                        println("${file.absolutePath} $i: ${lines[j]}")
                    }
                }
                else{
                    for(j in max(i-grek.b, 0) until min(lines.count(),i+grek.a+1)){
                        println("${file.absolutePath}: ${lines[j]}")
                    }
                }
            }
        }
    }
}