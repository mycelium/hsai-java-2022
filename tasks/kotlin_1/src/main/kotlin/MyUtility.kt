package ru.mironova.grek

object MyUtility {
    private val serviceSymbols = charArrayOf('^', '*', '?')
    fun checkPattron(regEx: String): Boolean {
        var isServiceLast = regEx[0] != '^'
        MAIN@ for (c in regEx.toCharArray()) {
            for (symbol in serviceSymbols) {
                if (c == symbol) {
                    return if (isServiceLast) {
                        false
                    } else {
                        isServiceLast = true
                        continue@MAIN
                    }
                }
            }
            isServiceLast = false
        }
        return true
    }

    fun reBuildPattren(regEx: String): String {
        return ".*$regEx.*"
    }
}