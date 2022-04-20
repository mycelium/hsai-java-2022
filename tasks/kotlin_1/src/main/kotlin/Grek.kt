package ru.mironova.grek

import ru.mironova.grek.Console.fileIsDir
import ru.mironova.grek.Console.fileNotExist
import ru.mironova.grek.Console.fileWithoutAccess
import ru.mironova.grek.Console.helpMessage
import ru.mironova.grek.Console.keyWithoutArg
import ru.mironova.grek.Console.notFileOrDir
import ru.mironova.grek.Console.uncorrectArg
import ru.mironova.grek.FileSpaceWorker.checkDir
import ru.mironova.grek.FileSpaceWorker.checkFile
import ru.mironova.grek.FileSpaceWorker.checkOnExclude
import ru.mironova.grek.FileSpaceWorker.getAllFile
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import java.util.function.Consumer
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

class Grek private constructor() {
    private var withRegister = false
    private var isRecursion = false
    private var isNumbered = false
    private var withSeparate = false
    private val paths = LinkedList<String>()
    private var specialMessage: String? = null
    private var regEx: String? = null
    private var nextMessage: String? = null
    private var messages: StringBuilder? = null
    private var after = 0
    private var before = 0
    private val uncorrectFile = LinkedList<String?>()

    private fun setBefore(before: String): Boolean {
        return try {
            this.before = before.toInt()
            if (this.before > -1) {
                withSeparate = true
                return true
            }
            false
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun setAfter(after: String): Boolean {
        return try {
            this.after = after.toInt()
            if (this.after > -1) {
                withSeparate = true
                return true
            }
            false
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun appendExclude(excludeWithPrefix: String): Boolean {
        val regEx = excludeWithPrefix.substring(10).trim { it <= ' ' }
        if (MyUtility.checkPattron(regEx)) {
            uncorrectFile.add(MyUtility.reBuildPattren(regEx))
            return true
        }
        return false
    }

    fun excute(): String? {
        if (specialMessage != null) {
            return specialMessage
        }
        messages = StringBuilder()
        val isMulti = paths.size > 1 || isRecursion
        try {
            for (path in paths) {
                val file = File(path)
                if (checkFile(file)) {
                    if (!checkOnExclude(file, uncorrectFile)) printFile(file, isMulti)
                    continue
                }
                if (checkDir(file)) {
                    if (isRecursion) {
                        val files = getAllFile(file, uncorrectFile)
                        files.forEach(Consumer { iterFile: File ->
                            printFile(
                                iterFile,
                                isMulti || files.size > 1
                            )
                        })
                    } else {
                        pullNextMessage(fileIsDir(file.name), false)
                    }
                    continue
                }
                pullNextMessage(notFileOrDir(file.name), false)
            }
        } catch (e: PatternSyntaxException) {
            return ""
        }
        popNextMessage(false)
        return messages.toString()
    }

    private fun pullNextMessage(newMessage: String?, withSeparator: Boolean) {
        if (newMessage != null) {
            popNextMessage(withSeparator)
            nextMessage = newMessage
        }
    }

    private fun popNextMessage(withSeparator: Boolean) {
        if (nextMessage != null) {
            messages!!.append(nextMessage)
            if (withSeparator && withSeparate) {
                messages!!.append(separator).append('\n')
            }
        }
    }

    @Throws(PatternSyntaxException::class)
    private fun printFile(file: File, isMulti: Boolean) {
        if (!file.canRead()) {
            pullNextMessage(fileWithoutAccess(file.name), false)
            return
        }
        try {
            val scanner = Scanner(file)
            val listBefore = LimitedLinkedList<String>(before)
            var counter = 0
            var lineNumber = 0
            var lastFind = 0
            var sidePrefix = ""
            var mainPrefix = ""
            var beFind = false
            if (isMulti) {
                sidePrefix = sidePrefix + file.path + "-"
                mainPrefix = mainPrefix + file.path + ":"
            }
            if (isNumbered) {
                sidePrefix = "$sidePrefix%d-"
                mainPrefix = "$mainPrefix%d:"
            }
            val pattern = Pattern.compile(regEx, if (withRegister) Pattern.CASE_INSENSITIVE else Pattern.CANON_EQ)
            while (scanner.hasNextLine()) {
                lineNumber++
                val line = scanner.nextLine()
                val matcher = pattern.matcher(line)
                if (matcher.find()) {
                    beFind = true
                    counter = 0
                    listBefore.add(String.format(mainPrefix, lineNumber) + line)
                    continue
                }
                ++counter
                if (after < counter && beFind) {
                    pullNextMessage(
                        listBefore.printAndClear(),
                        lastFind == 0 || lineNumber - after - 1 - before - counter > lastFind
                    )
                    lastFind = lineNumber - after - 1
                    beFind = false
                }
                if (beFind) {
                    listBefore.add(String.format(sidePrefix, lineNumber) + line)
                } else {
                    listBefore.addWithLimit(String.format(sidePrefix, lineNumber) + line)
                }
            }
            if (beFind) {
                pullNextMessage(
                    listBefore.printAndClear(),
                    lastFind == 0 || lineNumber - after - 1 - before - counter > lastFind
                )
            }
            scanner.close()
        } catch (e: FileNotFoundException) {
            pullNextMessage(fileNotExist(file.path), false)
        }
    }

    companion object {
        private const val separator = "--"

        fun init(args: Array<String>): Grek {
            val self = Grek()
            val len = args.size
            if (len < 2) {
                self.specialMessage = if (len == 1 && args[0] == Constants.helpKey) helpMessage() else ""
                return self
            }
            var iter = 0
            while (iter < len) {
                if (args[iter].trim { it <= ' ' }.length == 0) {
                    iter++
                    continue
                }
                when (args[iter]) {
                    Constants.recursionKey -> self.isRecursion = true
                    Constants.singleFile -> self.isRecursion = false
                    Constants.withoutRegisterKey -> self.withRegister = true
                    Constants.beforeStringKey -> if (len - 1 == iter) {
                        self.specialMessage = keyWithoutArg(args[iter])
                        return self
                    } else {
                        if (!self.setBefore(args[iter + 1])) {
                            self.specialMessage = uncorrectArg(args[iter + 1])
                            return self
                        }
                        iter++
                    }
                    Constants.afterStringKey -> if (len - 1 == iter) {
                        self.specialMessage = keyWithoutArg(args[iter])
                        return self
                    } else {
                        if (!self.setAfter(args[iter + 1])) {
                            self.specialMessage = uncorrectArg(args[iter + 1])
                            return self
                        }
                        iter++
                    }
                    Constants.helpKey -> {
                        self.specialMessage = helpMessage()
                        return self
                    }
                    else -> if (parseNonFixedKey(args[iter], self)) {
                        return self
                    }
                }
                iter++
            }
            return self
        }

        private fun parseNonFixedKey(key: String, self: Grek): Boolean {
            if (key.trim { it <= ' ' }.indexOf(Constants.excludeKey) == 0) {
                if (key.trim { it <= ' ' }.length < 11) {
                    self.specialMessage = uncorrectArg(key)
                    return true
                }
                self.appendExclude(key.trim { it <= ' ' })
            } else if (key.trim { it <= ' ' }[0] == '-') {
                self.specialMessage = uncorrectArg(key)
                return true
            } else if (self.regEx == null) {
                if (!MyUtility.checkPattron(key.trim { it <= ' ' })) {
                    self.specialMessage = ""
                    return true
                }
                self.regEx = MyUtility.reBuildPattren(key.trim { it <= ' ' })
            } else {
                if (key.trim { it <= ' ' }.length > 0) self.paths.add(key)
            }
            return false
        }

        fun initAndExcute(args: Array<String>): String {
            val grek = init(args)
            return if (grek != null) {
                grek.excute()!!.trim { it <= ' ' }
            } else {
                ""
            }
        }
    }
}