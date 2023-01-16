package com.mindyhsu.searchparkinglot.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.mindyhsu.searchparkinglot.AppApplication

object Util {
    fun isInternetConnected(): Boolean {
        val connectivityManager = AppApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return AppApplication.instance.getString(resourceId)
    }
}
