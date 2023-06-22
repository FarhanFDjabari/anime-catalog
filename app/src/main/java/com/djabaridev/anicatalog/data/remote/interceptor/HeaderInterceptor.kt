package com.djabaridev.anicatalog.data.remote.interceptor

import com.djabaridev.anicatalog.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AniCatalogHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
            .addHeader("X-MAL-CLIENT-ID", Constants.X_MAL_CLIENT_ID)
        return chain.proceed(builder.build())
    }
}