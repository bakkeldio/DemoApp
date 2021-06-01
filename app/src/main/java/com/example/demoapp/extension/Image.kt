package com.example.demoapp.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.demoapp.R

fun ImageView.load(url : String){
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_baseline_image_not_supported_24)
        .placeholder(R.drawable.ic_baseline_image_24)
        .fitCenter()
        .into(this)
}