package com.example.demoapp.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.demoapp.domain.model.ImageDomainModel
import com.example.demoapp.domain.usecase.GetImagesUseCase
import com.example.demoapp.presentation.adapter.UiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val PAGE_SIZE = 20

@HiltViewModel
class ImagesViewModel @Inject constructor(private val imagesUseCase: GetImagesUseCase) :
    ViewModel() {
    private val mutableLiveData: MutableLiveData<PagingData<UiModel>> = MutableLiveData()
    val liveData: LiveData<PagingData<UiModel>>
        get() = mutableLiveData

    init {
        viewModelScope.launch {
            imagesUseCase.getImagesAndAuthors()
                .map { pagingData ->
                    pagingData.map {
                        UiModel.ImageItem(it)
                    }

                }.map {
                    it.insertSeparators { before, after ->
                        if (after == null) {
                            return@insertSeparators null
                        }

                        if (before == null) {
                            return@insertSeparators null
                        }
                        if (after.imageItem.author.startsWith("A")) {
                            UiModel.SeparatorItem("Name of the author ${after.imageItem.author}")
                        } else {
                            null
                        }


                    }
                }.cachedIn(this).collect {
                    mutableLiveData.value = it
                }
        }
    }
}