package nz.co.test.transactions.presentation.transactions

import nz.co.test.transactions.domain.model.Transaction

data class TransactionListState(
    var isLoading: Boolean = false,
    var data: List<Transaction> = listOf(),
    var error: String = ""
)