package com.zukira.crud_mhs_berita_mi2a.service

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays

object ApiClient {

    //http://192.168.43.228:80/beritaDb/getBerita.php

    private const val BASE_URL = "http://192.168.43.228:80/beritaDb/"

    private  val client = OkHttpClient.Builder()
        .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
        .addInterceptor{chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit : BeritaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(interceptor())
            .client(client)//tambah
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeritaService::class.java)
    }

    fun interceptor() : OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

//    val beritaServices : BeritaService by lazy {
//        retrofit.create(BeritaService::class.java)
//    }

}