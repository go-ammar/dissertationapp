package com.project.collabexpense.di

import android.app.Application
import androidx.room.Room
import com.project.collabexpense.data.local.database.MyDatabase
import com.project.collabexpense.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(OkHttpClient.Builder().build())
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

}