package com.example.demoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.R
import com.example.demoapp.databinding.ItemRvBinding
import com.example.demoapp.domain.model.ImageDomainModel
import com.example.demoapp.extension.load

class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemRvBinding.bind(view)
    fun bind(imageDomainModel: ImageDomainModel?){
        imageDomainModel?.let {
            binding.author.text = it.author
            it.url?.let {
                binding.image.load(it)

            }
        }
    }

    companion object{
        fun create(parent: ViewGroup):ImageViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent, false)
            return ImageViewHolder(view)
        }
    }
}