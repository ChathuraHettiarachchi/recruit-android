package nz.co.test.transactions.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nz.co.test.transactions.domain.use_cases.GetAllTransactionsUseCase
import nz.co.test.transactions.utils.Resource
import java.text.SimpleDateFormat
import javax.inject.Inject

class TransactionListViewModel @Inject constructor(
    private val getAllTransactions: GetAllTransactionsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var transactionsState = MutableStateFlow<TransactionListState>(TransactionListState())
        private set

    fun populateAllTransactions() {
        viewModelScope.launch(dispatcher) {
            getAllTransactions().collectLatest {
                when (it) {
                    is Resource.Error -> transactionsState.value = TransactionListState(
                        isLoading = false,
                        error = it.message ?: "An unexpected error occurred"
                    )

                    is Resource.Loading -> transactionsState.value =
                        TransactionListState(isLoading = true)

                    is Resource.Success -> {
                        // sort descending data with transactionDate to get the latest to the top
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val sorted = it.data!!.sortedByDescending { item -> dateFormat.parse(item.transactionDate) }
                        transactionsState.value =
                            TransactionListState(isLoading = false, data = sorted)
                    }
                }
            }
        }
    }
}