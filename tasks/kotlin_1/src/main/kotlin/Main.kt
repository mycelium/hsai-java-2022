package ru.mironova.grek

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val grek = Grek.init(args)
        if (grek != null) {
            println(grek.excute()!!.trim { it <= ' ' })
        } else {
            println("")
        }
    }
}