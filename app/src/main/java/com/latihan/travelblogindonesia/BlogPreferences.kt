package com.latihan.travelblogindonesia

import android.content.Context
import android.content.SharedPreferences


class BlogPreferences internal constructor(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("travel-blog", Context.MODE_PRIVATE)
    var isLoggedIn: Boolean
        get() = preferences.getBoolean(KEY_LOGIN_STATE, false)
        set(loggedIn) {
            preferences.edit().putBoolean(KEY_LOGIN_STATE, loggedIn).apply()
        }

    companion object {
        private const val KEY_LOGIN_STATE = "key_login_state"
    }

}