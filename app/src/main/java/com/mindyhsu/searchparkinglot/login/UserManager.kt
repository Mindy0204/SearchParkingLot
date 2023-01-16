package com.mindyhsu.searchparkinglot.login

import android.content.Context
import com.mindyhsu.searchparkinglot.AppApplication

object UserManager {
    private const val EMAIL = "email"
    private const val OBJECT_ID = "objectId"
    private const val SESSION_TOKEN = "sessionToken"

    private val sharedPreferencesId =
        AppApplication.instance.getSharedPreferences(EMAIL, Context.MODE_PRIVATE)

    private val sharedPreferencesObjectId =
        AppApplication.instance.getSharedPreferences(OBJECT_ID, Context.MODE_PRIVATE)

    private val sharedPreferencesSessionToken =
        AppApplication.instance.getSharedPreferences(SESSION_TOKEN, Context.MODE_PRIVATE)

    var email: String?
        set(value) {
            sharedPreferencesId.edit().putString(EMAIL, value).apply()
        }
        get() {
            return sharedPreferencesId.getString(EMAIL, null)
        }

    var objectId: String?
        set(value) {
            sharedPreferencesObjectId.edit().putString(OBJECT_ID, value).apply()
        }
        get() {
            return sharedPreferencesObjectId.getString(OBJECT_ID, null)
        }

    var sessionToken: String?
        set(value) {
            sharedPreferencesSessionToken.edit().putString(SESSION_TOKEN, value).apply()
        }
        get() {
            return sharedPreferencesSessionToken.getString(SESSION_TOKEN, null)
        }
}
