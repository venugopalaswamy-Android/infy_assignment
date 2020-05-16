package com.android.infyassignment.viewmodel

import android.os.Build
import android.os.Looper.getMainLooper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.data.model.ClsRootFact
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class FactViewModelTest : KoinTest {

    private val factsViewModel: FactsViewModel by inject()


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var listOfFactsList: Observer<List<ClsFacts>>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun fetchFactsListFromDBInitially() {
        assertNotNull(factsViewModel.listOfFacts.value)
    }


    @Test
    fun fetchRootFactsFromDBInitially() {
        assertNull(factsViewModel.rootFact.value)
    }

    @Test
    fun fetchFactsResponseFromServer() {
        factsViewModel.callToGetFactsFromServer()
        Thread.sleep(5000)
        shadowOf(getMainLooper()).idle()
        assertEquals(factsViewModel.isErrorRaised.value, false)
    }

    @Test
    fun fetchFactsListAndRootDataFromDBAfterServerCall() {
        factsViewModel.listOfFacts.observeForever(listOfFactsList)
        factsViewModel.callToGetFactsFromServer()
        Thread.sleep(5000)
        shadowOf(getMainLooper()).idle()
        val value =
            factsViewModel.listOfFacts.value ?: error("No value from list")
        Mockito.verify(listOfFactsList).onChanged(value)

    }


    @After
    fun endTest() {
        stopKoin()
    }
}


