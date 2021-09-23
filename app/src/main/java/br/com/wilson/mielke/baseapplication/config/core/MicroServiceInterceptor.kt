package br.com.wilson.mielke.baseapplication.config.core

import okhttp3.Interceptor
import okhttp3.Response

class MicroServiceInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val requestBuilder = request.newBuilder()
        /*
        if (request.header("api-key").isNullOrBlank())
            requestBuilder.header("x-api-key", "apiKeyExample")

        */

        requestBuilder.header("Content-type", "application/json")

        request = requestBuilder.build()
        return chain.proceed(request)
    }
}
