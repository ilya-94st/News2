package com.example.news2.data.shared

import android.content.Context
import android.content.SharedPreferences
import com.example.news2.util.Constants

class SharedPref(context: Context) {
    private val KEY_LOGIN = "login"

    private val KEY_PASSWORD = "password"

    private val KEY_SAVE = "save"

    private val SAVE_QUERY = "save_query"

    val preferences: SharedPreferences = context.getSharedPreferences("Shared", Context.MODE_PRIVATE)

    var login: String
        get() = preferences.getString(KEY_LOGIN, "")!!
        set(value) = preferences.edit().putString(KEY_LOGIN, value).apply()

    var pasword: String
        get() = preferences.getString(KEY_PASSWORD, "")!!
        set(value) = preferences.edit().putString(KEY_PASSWORD, value).apply()

    var save: Boolean
        get() = preferences.getBoolean(KEY_SAVE, false)
        set(value) = preferences.edit().putBoolean(KEY_SAVE, value).apply()

    var saveQuery: String
        get() = preferences.getString(SAVE_QUERY, Constants.DEFAULT_VALUE_SEARCH)!!
        set(value) = preferences.edit().putString(SAVE_QUERY, value).apply()
}