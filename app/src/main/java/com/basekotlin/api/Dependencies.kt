package com.helpinhand_admin.api

import com.basekotlin.BuildConfig
import com.basekotlin.api.ServerAPI
import com.basekotlin.define.Constant.SERVER_ADDRESS
import com.google.gson.GsonBuilder
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import com.squareup.okhttp.logging.HttpLoggingInterceptor.Level.BODY
import com.squareup.okhttp.logging.HttpLoggingInterceptor.Level.NONE
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import timber.log.Timber
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit.SECONDS


object Dependencies {
    private val TIMEOUT_MAXIMUM = 30
    var serverAPI: ServerAPI? = null
        private set

    fun init() {
        val interceptor = provideHttpLoggingInterceptor()
        val client = provideOkHttpClientDefault(interceptor)
        serverAPI = provideRestApi(client)
    }

    fun provideRestApi(okHttpClient: OkHttpClient): ServerAPI {
        val gson = GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create()

        val builder = Retrofit.Builder()
                .baseUrl(SERVER_ADDRESS)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        return builder.build().create(ServerAPI::class.java!!)
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
        interceptor.level = if (BuildConfig.DEBUG) BODY else NONE
        return interceptor
    }

    fun provideOkHttpClientDefault(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient()
        client.interceptors().add(interceptor)
        client.interceptors().add(headersInterceptor())
        client.setConnectTimeout(TIMEOUT_MAXIMUM.toLong(), SECONDS)
        client.setReadTimeout(TIMEOUT_MAXIMUM.toLong(), SECONDS)
        client.setWriteTimeout(TIMEOUT_MAXIMUM.toLong(), SECONDS)
        return client
    }

    fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .build())
    }

}
