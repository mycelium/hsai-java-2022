package org.example

import kotlin.io.path.exists
import kotlin.io.path.isDirectory

object Checker {
    fun check(grek: Grek): Boolean {
        if(!grek.path.exists()) {
            return false
        }
        if(grek.a < 0){
            return false
        }
        if(grek.b < 0){
            return false
        }
        if(!grek.r && grek.path.isDirectory()){
            return false
        }
        return true
    }
}