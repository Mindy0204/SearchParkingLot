package com.mindyhsu.searchparkinglot.login

import android.content.Context
import com.mindyhsu.searchparkinglot.AppApplication

object UserManager {
    private const val EMAIL = "email"

    private val sharedPreferencesId =
        AppApplication.instance.getSharedPreferences(EMAIL, Context.MODE_PRIVATE)

    var email: String?
        set(value) {
            sharedPreferencesId.edit().putString(EMAIL, value).apply()
        }
        get() {
            return sharedPreferencesId.getString(EMAIL, null)
        }
}
