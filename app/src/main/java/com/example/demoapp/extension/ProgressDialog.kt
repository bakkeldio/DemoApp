package com.example.demoapp.extension

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.demoapp.R
import kotlinx.coroutines.*

private lateinit var dialog: AlertDialog

fun Fragment.loadingShow() {

    val builder = AlertDialog.Builder(activity!!)
    val inflater = activity!!.layoutInflater
    val view = inflater.inflate(R.layout.alert_loading, null)
    builder.setView(view)
    builder.setCancelable(false)
    dialog = builder.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}

fun Fragment.loadingHide() {

    CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            dialog.dismiss()
        }

    }
}
