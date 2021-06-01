package com.example.demoapp.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.R
import com.example.demoapp.databinding.ImagesLoadStateViewHolderBinding

class ImageLoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.images_load_state_view_holder, parent, false)) {
    private val binding = ImagesLoadStateViewHolderBinding.bind(itemView)
    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }


    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

}