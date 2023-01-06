package com.prismsoft.foody.di

import com.prismsoft.foody.util.Constants
import com.prismsoft.foody.network.FoodRecipesApi
import com.prismsoft.foody.network.HeadersInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHeadersInterceptor(): HeadersInterceptor =
        HeadersInterceptor(Constants.API_KEY)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun provideOkHttpClient(headersInterceptor: HeadersInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headersInterceptor)
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideRecipesApi(retrofit: Retrofit): FoodRecipesApi =
        retrofit.create(FoodRecipesApi::class.java)
}