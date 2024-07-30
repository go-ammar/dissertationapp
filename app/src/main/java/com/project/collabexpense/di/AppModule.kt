package com.project.collabexpense.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.project.collabexpense.data.local.dao.MyDao
import com.project.collabexpense.data.local.database.MyDatabase
import com.project.collabexpense.data.local.prefs.PreferenceHelper
import com.project.collabexpense.data.local.prefs.PreferenceManager
import com.project.collabexpense.data.remote.ApiService
import com.project.collabexpense.data.repository.AuthRepositoryImpl
import com.project.collabexpense.data.repository.BudgetRepositoryImpl
import com.project.collabexpense.data.repository.EditProfileRepositoryImpl
import com.project.collabexpense.data.repository.MyRepositoryImpl
import com.project.collabexpense.data.repository.TransactionRepositoryImpl
import com.project.collabexpense.domain.repository.AuthRepository
import com.project.collabexpense.domain.repository.BudgetRepository
import com.project.collabexpense.domain.repository.EditProfileRepository
import com.project.collabexpense.domain.repository.MyRepository
import com.project.collabexpense.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        preferenceDataStore: PreferenceHelper,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthorizationInterceptor(context, preferenceDataStore))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    class AuthorizationInterceptor(
        var context: Context,
        private val preferenceHelper: PreferenceHelper
    ) :
        Interceptor {
        private var response: Response? = null

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val accessToken = preferenceHelper.authToken
            var request: Request = chain.request()
            if (request.headers["Authorization"] == null || request.headers["Authorization"]!!.isEmpty() || request.headers["idToken"] == null || request.headers["idToken"]!!.isEmpty() || request.headers["refreshToken"] == null || request.headers["refreshToken"]!!.isEmpty()) {
                val headers = request.headers.newBuilder()
                    .add("Authorization", "Bearer $accessToken")
                    .build()
                Log.d("TAG", "intercept: Token $accessToken")
                request = request.newBuilder().headers(headers).build()

            }
            response = chain.proceed(request)
            return response as Response
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)



    @Provides
    @Singleton
    fun provideDatabase(app: Application): MyDatabase =
        Room.databaseBuilder(app, MyDatabase::class.java, "collab_expense")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMyDao(db: MyDatabase) = db.myDao()


    @Provides
    @Singleton
    fun provideMyRepository(
        apiService: ApiService,
        myDao: MyDao
    ): MyRepository = MyRepositoryImpl(apiService, myDao)

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
    ): AuthRepository = AuthRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideBudgetRepository(
        apiService: ApiService,
    ): BudgetRepository = BudgetRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideEditProfileRepository(
        apiService: ApiService,
    ): EditProfileRepository = EditProfileRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideTransactionRepository(
        apiService: ApiService,
    ): TransactionRepository = TransactionRepositoryImpl(apiService)

}