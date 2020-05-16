package com.android.infyassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.data.model.ClsRootFact
import com.android.infyassignment.repository.ViewModelRepository

class FactsViewModel(private val viewModelRepository: ViewModelRepository) : ViewModel() {

    var isErrorRaised = MutableLiveData<Boolean>()

    var listOfFacts = MutableLiveData<List<ClsFacts>>()

    var rootFact = MutableLiveData<ClsRootFact>()

    init {
        isErrorRaised.value = false
        getTotalFactsObjectFromDB()
        getRootFacts()
    }


    private fun getTotalFactsObjectFromDB(): LiveData<List<ClsFacts>> {
        listOfFacts.value = viewModelRepository.getTotalFactsListFromDB()
        return listOfFacts
    }


    fun callToGetFactsFromServer() {
        isErrorRaised.value = false
        viewModelRepository.getFactFromServer(object : ViewModelRepository.CallBackListener {
            override fun onSuccess(data: List<ClsFacts>, rootData: ClsRootFact) {
                listOfFacts.postValue(data)
                rootFact.postValue(rootData)
            }

            override fun onFailure(t: Throwable?) {
                isErrorRaised.value = true
            }

        })

    }

    private fun getRootFacts(): LiveData<ClsRootFact> {
        rootFact.value = viewModelRepository.getRootFactsData()
        return rootFact
    }

}
