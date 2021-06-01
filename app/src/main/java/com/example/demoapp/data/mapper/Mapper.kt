package com.example.demoapp.data.mapper

import com.example.demoapp.data.model.ImageModel
import com.example.demoapp.data.model.WeatherResponse
import com.example.demoapp.domain.model.ImageDomainModel
import com.example.demoapp.domain.model.WeatherDomainModel

fun WeatherResponse.toDomainModel(): WeatherDomainModel =
    WeatherDomainModel(name, clouds.all, main.temp.toInt(), main.humidity)


fun ImageModel.toDomainModel(): ImageDomainModel =
    ImageDomainModel(id.toInt(),download_url,author)