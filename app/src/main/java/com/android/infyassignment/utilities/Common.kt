package com.android.infyassignment.utilities

import android.content.Context
import android.net.ConnectivityManager

class Common {
    companion object {

        /**
         * function for checking the InterNet Connection.
         */
        fun verifyAvailableNetwork(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }


        /**
         * function  for String value check weather it is null or empty
         */
        fun isNullOrEmpty(str: String?): Boolean {
            if (str != null && str.trim().isNotEmpty())
                return false
            return true
        }
    }
}