package nz.co.test.transactions.domain.use_cases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.domain.repository.TransactionRepository
import nz.co.test.transactions.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.SocketException
import javax.inject.Inject

/**
 * GetAllTransactions is used to get all transaction list
 * Have used kotlin flow and will emit results from time to time
 */
class GetAllTransactionsUseCase @Inject constructor(private val repository: TransactionRepository) {
    /**
     * Used kotlin operator function, so it's corresponding member function is called automatically
     */
    operator fun invoke(): Flow<Resource<List<Transaction>>> = flow {
        try {
            emit(Resource.Loading<List<Transaction>>())
            val page = repository.retrieveTransactions()
            emit(Resource.Success<List<Transaction>>(page))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Transaction>>(
                    e.localizedMessage ?: "There is an exception occurred on HTTP Connection"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<List<Transaction>>(
                    e.localizedMessage
                        ?: "Couldn't connect to server. Please check the network connection"
                )
            )
        } catch (e: SocketException) {
            emit(
                Resource.Error<List<Transaction>>(
                    e.localizedMessage ?: "There is an exception occurred on Socket Connection"
                )
            )
        }
    }
}