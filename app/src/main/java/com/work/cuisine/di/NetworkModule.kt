package com.work.cuisine.di

import android.content.Context
import com.google.gson.Gson
import com.work.cuisine.R
import com.work.cuisine.network.Config
import com.work.cuisine.network.SpoonacularApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(context: Context): Config {
        val inputStream = context.resources.openRawResource(R.raw.config)
        val byteArray = ByteArrayOutputStream()

        var byte = inputStream.read()
        while (byte != -1) {
            byteArray.write(byte)
            byte = inputStream.read()
        }
        inputStream.close()
        return Gson().fromJson(byteArray.toString(Charsets.UTF_8.toString()), Config::class.java)
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(config: Config) = config.baseUrl

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(config: Config) = config.apiKey

    @Provides
    @Singleton
    fun provideRetrofit(@Named("baseUrl") baseUrl: String) =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun provideSpoonacularApi(retrofit: Retrofit) = retrofit.create(SpoonacularApi::class.java)
}