package com.newsappmvvm.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    /**From Local to UTC*/
    @SuppressLint("SimpleDateFormat")
    fun getNewsTime(input: String?): String {
        var output = ""
        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }.parse(input)
            output = SimpleDateFormat("yyyy-MM-dd, HH:mm a").format(simpleDateFormat!!)
        } catch (e: Exception) {
            e.message?.let { Log.e("date_exception", it) }
        }
        return output
    }
}