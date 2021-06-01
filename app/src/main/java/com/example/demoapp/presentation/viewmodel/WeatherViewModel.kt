package com.example.demoapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.demoapp.domain.ApiResult
import com.example.demoapp.domain.model.WeatherDomainModel
import com.example.demoapp.domain.usecase.GetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherInfoUseCase: GetWeatherInfoUseCase) :
    ViewModel() {


    fun login(): LiveData<ApiResult<WeatherDomainModel>> {

        return weatherInfoUseCase.getWeatherInfo().asLiveData(viewModelScope.coroutineContext)

    }
}