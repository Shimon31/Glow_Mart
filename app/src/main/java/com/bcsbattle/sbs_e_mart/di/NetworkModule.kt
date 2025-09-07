package com.bcsbattle.sbs_e_mart.di

import com.bcsbattle.sbs_e_mart.Service.ProductService
import com.bcsbattle.sbs_e_mart.utils.baseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

    }

    @Singleton
    @Provides
    fun provideServices(retrofit: Retrofit.Builder): ProductService {
        return retrofit.build().create(ProductService::class.java)

    }

}