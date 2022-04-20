package ru.mironova.grek

import java.util.*

class LimitedLinkedList<E>(private val limit: Int) : LinkedList<E>() {
    fun addWithLimit(e: E): Boolean {
        if (limit > 0) {
            if (limit - 1 < size) {
                removeFirst()
            }
            super.add(e)
        }
        return false
    }

    fun printAndClear(): String {
        val sb = StringBuilder()
        while (size > 0) {
            sb.append(pop()).append('\n')
        }
        return sb.toString()
    }
}