package com.meldcx.webcapture.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    //formating the time from timestamp
    fun getTime(timeStamp: Long): String {
        val formatter = SimpleDateFormat("hh:mm:ss dd MMM yyyy", Locale.getDefault())
        return formatter.format(Date(timeStamp))
    }
}