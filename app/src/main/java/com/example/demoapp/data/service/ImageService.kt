package com.example.demoapp.data.service

import com.example.demoapp.data.model.ImageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("/v2/list")
    suspend fun getImages(
        @Query("page") page : Int,
        @Query("limit") limit : Int
    ) : Response<List<ImageModel>>

}