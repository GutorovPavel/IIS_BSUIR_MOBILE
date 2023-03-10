package com.example.iismobile.common

import com.google.gson.GsonBuilder
import com.example.iismobile.common.data.ScheduleType
import com.example.iismobile.common.data.SimpleTime
import com.example.iismobile.common.typeadapters.GsonScheduleTypeAdapter
import com.example.iismobile.common.typeadapters.GsonSimpleTimeAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    inline fun <reified T> createService(baseUrl: String, client: OkHttpClient): T =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(ScheduleType::class.java, GsonScheduleTypeAdapter)
                        .registerTypeAdapter(SimpleTime::class.java, GsonSimpleTimeAdapter)
                        .create()
                )).client(client)
                .build()
                .create(T::class.java)


}