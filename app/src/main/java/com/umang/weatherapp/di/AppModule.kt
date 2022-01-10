@file:Suppress("unused", "unused", "unused")

package com.umang.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.umang.weatherapp.data.*
import com.umang.weatherapp.data.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Initialize objects used in app using App level module
 *
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {

        /**
         * To create retrofit instance
         *
         * @return : Retrofit service class
         */
        @Provides
        fun getRetroFitService(): APIService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }

        /**
         * To init database used in app
         *
         * @param context : Application context
         * @return : App db instance
         */
        @Provides
        fun buildDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "weather-db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    /**
     * To bind repository implementor
     *
     * @param weatherRepositoryImpl
     * @return
     */
    @Binds
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    /**
     * To bind local data source implementor
     *
     * @param localDataSourceImpl
     * @return
     */
    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    /**
     * To bind remote data source implementor
     *
     * @param remoteDataSourceImpl
     * @return
     */
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}