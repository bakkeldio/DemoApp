package com.example.demoapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demoapp.data.mapper.toDomainModel
import com.example.demoapp.data.model.ImageModel
import com.example.demoapp.data.service.ImageService
import com.example.demoapp.domain.model.ImageDomainModel
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject



class ImagePageSource @Inject constructor(private val imageService: ImageService) :
    PagingSource<Int, ImageDomainModel>() {
    override fun getRefreshKey(state: PagingState<Int, ImageDomainModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDomainModel> {


        val page = params.key ?: 1
        val pageSize = params.loadSize
        return try {
            val response = imageService.getImages(page, pageSize)

            if (response.isSuccessful) {
                val imagesModel: List<ImageModel> = response.body()!!

                val nextKey = if (imagesModel.isEmpty()){
                    null
                }else{
                    page +(params.loadSize/ImageRepositoryImpl.NETWORK_PAGE_SIZE)
                }
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(imagesModel.map {
                    it.toDomainModel()
                },prevKey,nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }


}
