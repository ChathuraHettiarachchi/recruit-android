package nz.co.test.transactions.utils

import java.lang.Exception
import java.math.BigDecimal

infix fun BigDecimal.calGST(percent: Double): String {
    return try {
        (this * BigDecimal(percent)).toFixedDecimal()
    } catch (e: Exception) {
        "N/A"
    }
}