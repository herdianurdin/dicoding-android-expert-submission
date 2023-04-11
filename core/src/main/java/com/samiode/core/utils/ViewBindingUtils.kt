package com.samiode.core.utils

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

object ViewBindingUtils {
    inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
        crossinline bindingInflater: (LayoutInflater) -> T
    ) = lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
}