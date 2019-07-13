package com.noorifytech.coupon.data.dao.backend.impl.retrofit

import com.noorifytech.coupon.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {

    private val BASE_URL = "https://noorifytech.%1\$s"

    val defaultRetrofit: Retrofit
        get() = getDefaultRetrofit(BASE_URL)

    fun getDefaultRetrofit(baseUrl: String): Retrofit {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        //    String environmentExtension = SessionManager.getInstance().getGlobalInfo().getEnvironmentExtension();
        //    if (environmentExtension.equals("com")) {
        //      clientBuilder = new OkHttpClient.Builder();
        //    } else {
        //      clientBuilder = SelfSigningClientBuilder.getUnsafeOkHttpClient();
        //    }

        val environmentExtension = "com"

        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        clientBuilder.addInterceptor(logging)
        val client = clientBuilder.build()

        val builder = Retrofit.Builder()
            .client(client)//
            .baseUrl(String.format(baseUrl, environmentExtension) + "/")
            .addConverterFactory(GsonConverterFactory.create())

        return builder.build()
    }
}
