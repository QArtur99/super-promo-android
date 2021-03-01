package com.superpromo.superpromo.ui.util

object PreventMultiClick {
    var lastClick = 0L

    fun isLock(): Boolean {
        if (System.currentTimeMillis() - lastClick < 200) {
            return true
        }
        lastClick = System.currentTimeMillis()
        return false
    }
}