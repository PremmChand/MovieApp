package com.example.movieapp.utils

import com.example.movieapp.domain.ApiInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val gson: Gson? = GsonBuilder()
        .serializeNulls()
        .create()
    val api: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.Base)
            .addConverterFactory(gson?.let { GsonConverterFactory.create(it) })
            .build()
            .create(ApiInterface::class.java)
    }
}