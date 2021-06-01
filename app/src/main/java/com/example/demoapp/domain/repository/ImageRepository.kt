package com.example.demoapp.domain.repository

import androidx.paging.PagingData
import com.example.demoapp.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository{

    fun getResult(): Flow<PagingData<ImageDomainModel>>
}