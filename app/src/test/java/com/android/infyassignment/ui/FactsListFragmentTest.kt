package com.android.infyassignment.ui


import android.os.Build
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.infyassignment.ui.facts.FactsListFragment
import com.android.infyassignment.ui.facts.FactsListScreen
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class FactsListFragmentTest : KoinTest{

    private lateinit var factsListScreen: FactsListScreen
    private lateinit var factsListFragment: FactsListFragment
    private lateinit var activityController: ActivityController<FactsListScreen>

    @Before
    fun setUp() {
        activityController = Robolectric.buildActivity(FactsListScreen::class.java)
        factsListScreen = activityController.get()
        activityController.create()
        factsListFragment = FactsListFragment()
        startFragment(factsListFragment)
    }


    @Test
    fun isFactsListScreenCreated() {
        assertNotNull(factsListScreen)
    }

    @Test
    fun isFragmentAttachedToActivity() {
        assertNotNull(factsListFragment.getAttachedContext())
    }



    private fun startFragment(factsListFragment: FactsListFragment) {
        val fragmentManager: FragmentManager = factsListScreen.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(factsListFragment, null).commit()
    }

    @After
    fun endTest() {
        stopKoin()
    }


}