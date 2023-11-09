package nz.co.test.transactions.domain.repository

import nz.co.test.transactions.domain.model.Transaction

interface TransactionRepository {
    suspend fun retrieveTransactions(): List<Transaction>
}