package nz.co.test.transactions.data.repositoryImpl

import nz.co.test.transactions.data.remote.TransactionsService
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * TransactionsService implementation from domain repository. This implements the actions defined in the
 * repo of domain package.
 * Reason is to support great scalability.
 */
class TransactionRepositoryImpl @Inject constructor(private val service: TransactionsService):
    TransactionRepository {

    /**
     * Get all transactions
     */
    override suspend fun retrieveTransactions(): List<Transaction> {
        return service.retrieveTransactions()
    }
}