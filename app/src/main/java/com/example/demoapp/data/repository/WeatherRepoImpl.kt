package com.example.demoapp.data.repository

import com.example.demoapp.data.mapper.toDomainModel
import com.example.demoapp.data.service.ApiService
import com.example.demoapp.data.model.WeatherResponse
import com.example.demoapp.domain.ApiResult
import com.example.demoapp.domain.model.WeatherDomainModel
import com.example.demoapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepoImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override fun getWeatherInfo(): Flow<ApiResult<WeatherDomainModel>> {
        return flow {
            try {
                emit(ApiResult.Loading)
                val response = apiService.getWeatherInfo()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ApiResult.Success(it.toDomainModel()))
                    } ?: emit(ApiResult.EmptyResult("Данные пусты"))

                } else {
                    emit(ApiResult.Error("Ошибка при получении данных"))
                }
            } catch (e: Exception) {
                emit(ApiResult.NetworkError("Проверьте интернет подключение"))
            }
        }.flowOn(Dispatchers.IO)

    }
}