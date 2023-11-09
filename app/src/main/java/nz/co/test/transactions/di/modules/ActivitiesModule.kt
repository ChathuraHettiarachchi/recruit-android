package nz.co.test.transactions.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import nz.co.test.transactions.di.ActivityClassKey
import nz.co.test.transactions.presentation.transactions.TransactionListActivity

@Module
class ActivitiesModule {

    @Provides
    @IntoMap
    @ActivityClassKey(TransactionListActivity::class)
    fun providesMainActivity(): Activity = TransactionListActivity()
}