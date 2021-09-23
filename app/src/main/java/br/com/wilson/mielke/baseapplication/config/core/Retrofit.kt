package br.com.wilson.mielke.baseapplication.config.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit{

    operator fun invoke(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
                .Builder()
                .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
