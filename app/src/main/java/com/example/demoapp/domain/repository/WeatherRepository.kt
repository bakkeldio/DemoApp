package com.example.demoapp.domain.repository

import com.example.demoapp.domain.ApiResult
import com.example.demoapp.domain.model.WeatherDomainModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherInfo() : Flow<ApiResult<WeatherDomainModel>>
}