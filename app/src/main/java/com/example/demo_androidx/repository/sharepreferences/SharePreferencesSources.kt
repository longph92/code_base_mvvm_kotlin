package com.example.demo_androidx.repository.sharepreferences

import android.content.SharedPreferences
import javax.inject.Inject

class SharePreferencesSources @Inject constructor(private val sharePreferences: SharedPreferences) {
    companion object {
        const val TOKEN = "sf_token"
    }

    var token: String? get() = sharePreferences.getString(TOKEN, null)
    set(value) {
        sharePreferences.edit().putString(TOKEN, value).apply()
    }
}