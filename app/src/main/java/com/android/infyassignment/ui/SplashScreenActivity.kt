package com.android.infyassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.android.infyassignment.R
import com.android.infyassignment.ui.facts.FactsListScreen

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Navigating to the Facts list Screen after 3 secs
        Handler().postDelayed({
            startActivity(Intent(this, FactsListScreen::class.java))
            finish()
        }, 3000)
    }
}
