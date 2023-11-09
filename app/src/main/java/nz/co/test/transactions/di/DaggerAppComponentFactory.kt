package nz.co.test.transactions.di

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AppComponentFactory
import androidx.core.app.ComponentActivity
import nz.co.test.transactions.di.component.DaggerAppComponent
import nz.co.test.transactions.presentation.transactions.TransactionListActivity
import javax.inject.Inject
import javax.inject.Provider

class DaggerAppComponentFactory : AppComponentFactory() {

    private val component = DaggerAppComponent.create()

    init {
        component.inject(this)
    }

    @Inject
    lateinit var map: Map<Class<out Activity>, @JvmSuppressWildcards Provider<Activity>>

    override fun instantiateActivityCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): Activity {
        val activityClass = cl.loadClass(className)

        return if (activityClass == TransactionListActivity::class.java) {
            val activity = map.getValue(TransactionListActivity::class.java)
            activity.get()
        } else super.instantiateActivityCompat(cl, className, intent)
    }
}