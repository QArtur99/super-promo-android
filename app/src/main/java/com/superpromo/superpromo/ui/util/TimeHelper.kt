package com.superpromo.superpromo.ui.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object TimeHelper {

    fun getDateFormat(): SimpleDateFormat {
        val df = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        return df
    }
}
