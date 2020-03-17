package com.example.demo_androidx.di

import android.app.Application
import android.content.Context
import com.example.demo_androidx.constants.Constants
import com.example.demo_androidx.repository.remote.ApiService
import com.example.demo_androidx.repository.sharepreferences.SharePreferencesSources
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class InternetModule {

    @Provides
    @Singleton
    fun provideSharePreferences(application: Application): SharePreferencesSources =
        SharePreferencesSources(application.getSharedPreferences(Constants.SHARE_PREFERENCE, Context.MODE_PRIVATE))

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    @Singleton
    fun providesInterceptor(app: Application): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
//                .addHeader(
//                    "Authorization",
//                    "Bearer ${provideSharePreferences(app).token ?: ""}"
//                )
            chain.proceed(newRequest.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application,
                            header: Interceptor
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(header)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }
}