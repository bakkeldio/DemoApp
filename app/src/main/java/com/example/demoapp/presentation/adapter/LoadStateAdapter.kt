package com.example.demoapp.presentation.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ImageLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ImageLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ImageLoadStateViewHolder {
        return ImageLoadStateViewHolder(parent,retry)
    }
}