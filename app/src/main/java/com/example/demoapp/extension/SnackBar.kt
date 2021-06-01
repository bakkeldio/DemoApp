package com.example.demoapp.extension

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(message: String){
    Snackbar.make(this.requireView(),message, Snackbar.LENGTH_LONG).show()
}