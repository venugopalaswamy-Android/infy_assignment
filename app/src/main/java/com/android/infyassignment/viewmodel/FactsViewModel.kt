package com.android.infyassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.data.model.ClsFactsResponse
import com.android.infyassignment.data.model.ClsRootFact
import com.android.infyassignment.repository.ViewModelRepository

class FactsViewModel(private val viewModelRepository: ViewModelRepository) : ViewModel() {

    var isErrorRaised = MutableLiveData<Boolean>()

    var listOfFacts = MutableLiveData<List<ClsFacts>>()

    init {
        isErrorRaised.value = false
        // listOfFacts.value = listOf()
    }


    fun getTotalFactsObjectFromDB(): LiveData<List<ClsFacts>> {
        return viewModelRepository.getTotalFactsListFromDB()
    }


    fun callToGetFactsFromServer() {
        isErrorRaised.value = false
        viewModelRepository.getFactFromServer(object : ViewModelRepository.CallBackListener {
            override fun onSuccess(data: ClsFactsResponse) {
                listOfFacts.value = data.clsFacts
            }

            override fun onFailure(t: Throwable?) {
                isErrorRaised.value = true
            }

        })

    }

    fun getRootFacts(): LiveData<ClsRootFact> {
        return viewModelRepository.getRootFactsData()
    }

}
