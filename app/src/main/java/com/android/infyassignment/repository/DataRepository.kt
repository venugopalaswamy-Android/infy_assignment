package com.android.infyassignment.repository

import com.android.infyassignment.data.model.ClsFactsResponse
import com.android.infyassignment.data.network.APIInterface
import com.android.infyassignment.utilities.keyStrValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(val apiInterface: APIInterface) {

    /**
     * function to call the Fact Get from server.
     */
    fun getFactFromServer(listener:  CallBackListener){
        apiInterface.getFactsFromServer(keyStrValue).enqueue(object : Callback<ClsFactsResponse>{
            override fun onFailure(call: Call<ClsFactsResponse>, t: Throwable) {
                listener.onFailure()
            }

            override fun onResponse(
                call: Call<ClsFactsResponse>,
                response: Response<ClsFactsResponse>
            ) {
                if(response.body() != null){
                    listener.onSuccess((response.body() as ClsFactsResponse))
                }else{
                    listener.onFailure()
                }

            }

        })
    }


    /**
     * interface to give the CallBacks on the Response from Server.
     */
    interface CallBackListener{
        fun onSuccess(data: ClsFactsResponse);
        fun onFailure()
    }
}