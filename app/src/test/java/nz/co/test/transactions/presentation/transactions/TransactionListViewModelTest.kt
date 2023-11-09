package nz.co.test.transactions.presentation.transactions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.domain.use_cases.GetAllTransactionsUseCase
import nz.co.test.transactions.utils.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TransactionListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getAllTransactionsUseCase: GetAllTransactionsUseCase

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: TransactionListViewModel
    private lateinit var transactions: List<Transaction>

    val mockTransactions = listOf(
        Transaction(
            1,
            "2023-11-10",
            "Transaction 1",
            100.toBigDecimal(),
            0.toBigDecimal()
        )
    )

    @Before
    fun setup() {
        viewModel = TransactionListViewModel(getAllTransactionsUseCase, testDispatcher)
    }

    @Test
    fun `populateAllTransactions should update transactionsState with success data`() = runTest {
        `when`(getAllTransactionsUseCase.invoke()).thenReturn(
            flowOf(
                Resource.Success(
                    mockTransactions
                )
            )
        )

        viewModel.populateAllTransactions()

        assertEquals(
            viewModel.transactionsState.value,
            TransactionListState(data = mockTransactions)
        )
    }

    @Test
    fun `populateAllTransactions should update transactionsState with error message`() = runTest {
        val errorMessage = "Error occurred"
        `when`(getAllTransactionsUseCase.invoke()).thenReturn(flowOf(Resource.Error(errorMessage)))

        viewModel.populateAllTransactions()

        assertEquals(viewModel.transactionsState.value, TransactionListState(error = errorMessage))
    }

    @Test
    fun `populateAllTransactions should emit loading state and then success state with data`() =
        runTest {

            val loadingState = Resource.Loading<List<Transaction>>()
            val successState = Resource.Success(mockTransactions)
            val mockFlow = MutableStateFlow<Resource<List<Transaction>>>(loadingState)
            `when`(getAllTransactionsUseCase.invoke()).thenReturn(mockFlow)

            viewModel.populateAllTransactions()

            viewModel.transactionsState.test {
                assertEquals(TransactionListState(isLoading = true), awaitItem())

                // Emit success state with data
                mockFlow.value = successState

                assertEquals(TransactionListState(isLoading = false, data = mockTransactions), awaitItem())
            }
        }
}