package com.example.demoapp.domain.usecase

import com.example.demoapp.domain.repository.ImageRepository
import javax.inject.Inject


class GetImagesUseCase @Inject constructor(private val repository: ImageRepository) {

    fun getImagesAndAuthors() = repository.getResult()
}