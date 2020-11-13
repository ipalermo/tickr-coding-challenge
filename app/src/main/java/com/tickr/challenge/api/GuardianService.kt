package com.tickr.challenge.api

import com.tickr.challenge.BuildConfig
import com.tickr.challenge.data.GuardianSearchResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to connect to The Guardian API to fetch news
 */
interface GuardianService {

    @GET("search")
    fun searchContent(
        @Query("order-by") orderBy: String,
        @Query("show-fields") showFields: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("q") query: String,
        @Query("api-key") apiKey: String = BuildConfig.GUARDIAN_ACCESS_KEY

    ): Single<GuardianSearchResponse>

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com/"

        fun create(): GuardianService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GuardianService::class.java)
        }
    }
}
