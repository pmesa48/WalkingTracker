package com.pmesa48.pablomesa_challenge.source.api.common

import com.pmesa48.pablomesa_challenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class FlickrInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_PARAM, BuildConfig.FLICKR_API_KEY)
            .addQueryParameter(FORMAT_PARAM, FORMAT_VALUE)
            .addQueryParameter(JSON_CALLBACK_PARAM, JSON_CALLBACK_VALUE)
            .build()
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val API_KEY_PARAM = "api_key"
        const val FORMAT_PARAM = "format"
        const val FORMAT_VALUE = "json"
        const val JSON_CALLBACK_PARAM = "nojsoncallback"
        const val JSON_CALLBACK_VALUE = "1"

    }
}