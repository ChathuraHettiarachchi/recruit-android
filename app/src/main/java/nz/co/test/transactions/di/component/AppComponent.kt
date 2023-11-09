package nz.co.test.transactions.di.component

import dagger.Component
import nz.co.test.transactions.di.DaggerAppComponentFactory
import nz.co.test.transactions.di.modules.ActivitiesModule
import nz.co.test.transactions.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ActivitiesModule::class
    ]
)
interface AppComponent {
    fun inject(appComponent: DaggerAppComponentFactory)
}