package ru.mironova.grek

import java.io.File
import java.util.*
import java.util.regex.Pattern

object FileSpaceWorker {
    fun getAllFile(file: File, excludes: LinkedList<String?>): LinkedList<File> {
        val files = LinkedList<File>()
        MAIN@ for (iterFile in file.listFiles()) {
            if (!iterFile.canRead()) {
                files.add(iterFile)
                continue
            }
            if (iterFile.isDirectory) {
                files.addAll(getAllFile(iterFile, excludes))
                continue
            }
            if (iterFile.isFile) {
                if (checkOnExclude(iterFile, excludes)) {
                    continue@MAIN
                }
                files.add(iterFile)
                continue
            }
        }
        return files
    }

    fun checkOnExclude(file: File, excludes: LinkedList<String?>): Boolean {
        for (regEx in excludes) {
            if (Pattern.matches(regEx, file.name)) {
                return true
            }
        }
        return false
    }

    fun checkFile(file: File): Boolean {
        if (!file.exists()) {
            return false
        }
        if (file.isDirectory) {
            return false
        }
        return file.canRead()
    }

    fun checkDir(file: File): Boolean {
        if (!file.exists()) {
            return false
        }
        if (file.isFile) {
            return false
        }
        return file.canRead()
    }
}