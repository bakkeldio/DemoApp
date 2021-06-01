package com.example.demoapp.presentation.adapter

import com.example.demoapp.domain.model.ImageDomainModel

sealed class UiModel {
    data class ImageItem(val imageItem: ImageDomainModel) : UiModel()
    data class SeparatorItem(val description :String) : UiModel()
}