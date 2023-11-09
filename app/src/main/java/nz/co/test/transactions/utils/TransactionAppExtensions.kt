package nz.co.test.transactions.utils

import android.icu.text.DecimalFormat
import java.lang.Exception
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.TimeZone

fun String.convertToFormattedDateTime(): String {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val outputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    return try {
        // Set input date-time format time zone to UTC
        inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputDateFormat.parse(this)
        outputDateFormat.timeZone = TimeZone.getDefault()
        outputDateFormat.format(date)
    } catch (e: ParseException) {
        "N/A"
    }
}

fun BigDecimal.toFixedDecimal(): String {
    val decimalFormat = DecimalFormat("#.##")
    return decimalFormat.format(this)
}