package com.example.demoapp.domain.usecase

import com.example.demoapp.domain.ApiResult
import com.example.demoapp.domain.model.WeatherDomainModel
import com.example.demoapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    fun getWeatherInfo() : Flow<ApiResult<WeatherDomainModel>>{
        return weatherRepository.getWeatherInfo()
    }
}