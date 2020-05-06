package com.android.infyassignment.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.infyassignment.data.model.ClsFactsResponse
import com.android.infyassignment.repository.DataRepository

class FactsViewModel(val dataRepository: DataRepository) : ViewModel() {

    var clsFactsResponse = MutableLiveData<ClsFactsResponse>()
    var isErrorRaised = MutableLiveData<Boolean>()

    //initalizing the Livedata values with defaults.
    init {
        clsFactsResponse.value = ClsFactsResponse(listOf(),"")
        isErrorRaised.value = false
    }

    /**
     * function to fetch the facts from server
     */
    fun getFacts(){
        dataRepository.getFactFromServer(object : DataRepository.CallBackListener{
            override fun onSuccess(data: ClsFactsResponse) {
               clsFactsResponse.value = data
            }

            override fun onFailure() {
                isErrorRaised.value = true
            }

        })
    }
}