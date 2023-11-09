package nz.co.test.transactions.data.remote

import nz.co.test.transactions.domain.model.Transaction
import retrofit2.http.GET

/**
 * Transaction service to call transaction related apis
 */
interface TransactionsService {
    @GET(APIEndpoints.ALL_TRANSACTIONS)
    suspend fun retrieveTransactions(): List<Transaction>
}

