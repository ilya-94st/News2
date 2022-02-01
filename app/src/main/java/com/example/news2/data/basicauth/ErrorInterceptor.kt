package com.example.news2.data.basicauth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       val request: Request = chain.request()
       val response = chain.proceed(request)
       when(response.code) {
           400 -> {

           }
           401 -> {

           }
           402 -> {

           }
           403 -> {

           }
           404 -> {

           }
           405 -> {

           }
       }
        return response
    }
}