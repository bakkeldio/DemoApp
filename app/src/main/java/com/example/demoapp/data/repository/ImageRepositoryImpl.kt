package com.example.demoapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.demoapp.data.service.ImageService
import com.example.demoapp.domain.model.ImageDomainModel
import com.example.demoapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(private val imageService: ImageService) : ImageRepository {
    override fun getResult(): Flow<PagingData<ImageDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE,enablePlaceholders = false),
            pagingSourceFactory = {ImagePageSource(imageService)}
        ).flow
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}