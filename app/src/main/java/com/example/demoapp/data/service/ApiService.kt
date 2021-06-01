package com.example.demoapp.data.service

import com.example.demoapp.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/data/2.5/weather")
    suspend fun getWeatherInfo(
        @Query("id") id : Int = 498817
    ):Response<WeatherResponse>
}