package com.example.demoapp.data.di

import android.util.Log
import androidx.paging.PagingSource
import com.example.demoapp.data.repository.ImagePageSource
import com.example.demoapp.data.repository.ImageRepositoryImpl
import com.example.demoapp.data.repository.WeatherRepoImpl
import com.example.demoapp.data.service.ApiService
import com.example.demoapp.data.service.ImageService
import com.example.demoapp.domain.model.ImageDomainModel
import com.example.demoapp.domain.repository.ImageRepository
import com.example.demoapp.domain.repository.WeatherRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun authInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url
                .newBuilder()
                .build()

            val newUrl =
                url.newBuilder().addQueryParameter("appid", "c35880b49ff95391b3a6d0edd0c722eb")
                    .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger = {
            Log.d("OkHttp", it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @WeatherOkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpWeather(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @ImageOkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpImage(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@WeatherOkHttpClient okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageService(@ImageOkHttpClient okHttpClient: OkHttpClient): ImageService {
        return Retrofit.Builder().baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(okHttpClient)
            .build().create(ImageService::class.java)
    }


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ImageOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class WeatherOkHttpClient
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideImagePageSource(imagePageSource: ImagePageSource): PagingSource<Int, ImageDomainModel>

    @Singleton
    @Binds
    abstract fun provideImageRepository(imageRepositoryImpl: ImageRepositoryImpl):ImageRepository

    @Singleton
    @Binds
    abstract fun provideWeatherRepository(weatherRepoImpl: WeatherRepoImpl): WeatherRepository


}