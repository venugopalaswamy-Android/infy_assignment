package com.android.infyassignment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.startKoin

class ApplicationLevel : Application() {
    private val TAG = ApplicationLevel::class.qualifiedName

    override fun onCreate() {
        super.onCreate()
        // For DI
        startKoin(
            this,
            listOf(mainModule),
            loadPropertiesFromFile = true
        )
    }

    companion object {

        /**
         * function for checking the InterNet Connection.
         */
        fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
            val connectivityManager =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }


        /**
         * function  for String value check weather it is null or empty
         */
        fun isNullOrEmpty(str: String?): Boolean {
            if (str != null && !str.trim().isEmpty())
                return false
            return true
        }
    }

}