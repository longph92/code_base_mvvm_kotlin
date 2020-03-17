package com.example.demo_androidx.utils

import android.content.Context
import android.widget.Toast
import com.example.demo_androidx.repository.errormanager.Error

fun Error.toast(context: Context) {
    Toast.makeText(context, this.message, Toast.LENGTH_SHORT).show()
}