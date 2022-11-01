package com.study.presentation.v2.di

import com.google.gson.GsonBuilder
import com.study.data.BuildConfig
import com.study.data.repository.remote.api.AgriculturalProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    private val AGRICULTURAL_API_PATH="http://211.237.50.150:7080/openapi/"

    @Provides
    fun getRetrofitApi(): AgriculturalProductApi {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("$AGRICULTURAL_API_PATH/openapi/${BuildConfig.agriculturalAPIkey}/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(AgriculturalProductApi::class.java)
    }
}