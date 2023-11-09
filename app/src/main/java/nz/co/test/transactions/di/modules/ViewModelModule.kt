package nz.co.test.transactions.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nz.co.test.transactions.di.ViewModelKey
import nz.co.test.transactions.factory.ViewModelFactory
import nz.co.test.transactions.presentation.transactions.TransactionListViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TransactionListViewModel::class)
    abstract fun bindTransactionListViewModel(transactionListViewModel: TransactionListViewModel): ViewModel
}