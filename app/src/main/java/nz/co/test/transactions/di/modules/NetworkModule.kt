package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import nz.co.test.transactions.data.remote.TransactionsService
import nz.co.test.transactions.data.repositoryImpl.TransactionRepositoryImpl
import nz.co.test.transactions.domain.repository.TransactionRepository
import nz.co.test.transactions.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    /**
     * OkHttpClient from connection timeouts and logs
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Retrofit instance for the application
     */
    @Provides
    @Singleton
    fun providesTransactionsService(client: OkHttpClient): TransactionsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionsService::class.java)
    }

    /**
     * TransactionRepository for use case usage
     */
    @Provides
    @Singleton
    fun providesTransactionRepository(service: TransactionsService): TransactionRepository {
        return TransactionRepositoryImpl(service)
    }

    /**
     * Default dispatcher with IO
     */
    @Provides
    fun providesCoroutine(): CoroutineDispatcher = Dispatchers.IO
}