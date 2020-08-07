package com.vrb.apps.themealdb.di

import com.google.gson.GsonBuilder
import com.vrb.apps.themealdb.BuildConfig
import com.vrb.apps.themealdb.data.remote.api.MealDBApi
import com.vrb.apps.themealdb.data.models.MealsList
import com.vrb.apps.themealdb.utils.deserializer.MealsDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val CONNECT_TIMEOUT = 120L
private const val READ_TIMEOUT = 120L
private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideMealDBApi(get()) }
}

fun provideMealDBApi(client: OkHttpClient): MealDBApi = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(createGsonConverter())
//    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
    .create(MealDBApi::class.java)

fun provideOkHttpClient(): OkHttpClient {
    val httpInterceptor = HttpLoggingInterceptor()
    httpInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
//        client.addInterceptor(httpInterceptor)
    }

    return client.build()
}

private fun createGsonConverter(): GsonConverterFactory {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(MealsList::class.java, MealsDeserializer())
    val gson = gsonBuilder.create()

    return GsonConverterFactory.create(gson)
}