package com.zetung.gifsgiver.util

import android.util.Log
import okhttp3.Interceptor

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        Log.d("Retrofit request",request.toString())
        val response = chain.proceed(request)
        Log.d("Retrofit response", response.toString())
        return response
    }
}