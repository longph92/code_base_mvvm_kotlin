package com.example.demo_androidx

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:loadImages")
fun loadImages(view: ImageView, src: Int) {
    view.setImageResource(src)
}
