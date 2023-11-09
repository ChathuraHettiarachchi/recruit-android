package nz.co.test.transactions.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Transaction(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("transactionDate") val transactionDate: String,
    @field:SerializedName("summary") val summary: String,
    @field:SerializedName("debit") val debit: BigDecimal,
    @field:SerializedName("credit") val credit: BigDecimal
) : Parcelable