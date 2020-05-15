package com.android.infyassignment.ui

import android.os.Build
import android.widget.TextView
import com.android.infyassignment.R
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SplashScreenTest {

    private lateinit var activity: SplashScreenActivity
    private lateinit var activityController: ActivityController<SplashScreenActivity>

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(SplashScreenActivity::class.java)
        activity = activityController.get()
        activityController.create()
    }


    @Test
    fun isSplashScreenVisible() {
        assertNotNull(activity)
    }


    @Test
    fun checkTextIsSameOrNot() {
        val txtSplash = activity.findViewById<TextView>(R.id.splash_txt)
        assertEquals(txtSplash.text, "InfyAssignment")
    }

    @After
    fun tearDown() {
        activityController.destroy()
        stopKoin()
    }


}