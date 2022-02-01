package com.example.news2.data.basicauth

import com.example.news2.presentation.ui.prefs
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       val login = prefs.login
        val password = prefs.pasword
        val credential: String = Credentials.basic(login, password)
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credential).build()
        return  chain.proceed(request)
    }
}