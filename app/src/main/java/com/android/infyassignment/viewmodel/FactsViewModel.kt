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

    init {
        isErrorRaised.value = false
    }


    fun getTotalFactsObjectFromDB(): LiveData<List<ClsFacts>> {
        return viewModelRepository.getTotalResponseObjFromDB()
    }

    fun callToGetFactsFromServer() {
        isErrorRaised.value = false
        viewModelRepository.getFactFromServer(object : ViewModelRepository.CallBackListener {
            override fun onSuccess(data: ClsFactsResponse) {

            }

            override fun onFailure() {
                isErrorRaised.value = true
            }

        })

    }

    fun getRootFacts(): LiveData<ClsRootFact> {
        return viewModelRepository.getRootFactsData()
    }


}