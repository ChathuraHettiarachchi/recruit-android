package nz.co.test.transactions.utils

import android.view.View
import nz.co.test.transactions.domain.model.Transaction

interface ItemOnClickListener {
    fun onTransactionItemClick(position: Int, transaction: Transaction)
}