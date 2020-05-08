package com.android.infyassignment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.android.infyassignment.data.db.FactDao
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.data.model.ClsFactsResponse
import com.android.infyassignment.data.model.ClsRootFact
import com.android.infyassignment.data.network.APIInterface
import com.android.infyassignment.utilities.keyStrValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelRepository( private val apiInterface: APIInterface, val factDao: FactDao) {

    /**
     * function to call the Fact Get from server.
     */
    fun getFactFromServer(listener: CallBackListener) {

        apiInterface.getFactsFromServer(keyStrValue).enqueue(object : Callback<ClsFactsResponse> {
            override fun onFailure(call: Call<ClsFactsResponse>, t: Throwable) {
                listener.onFailure()
            }

            override fun onResponse(
                call: Call<ClsFactsResponse>,
                response: Response<ClsFactsResponse>
            ) {
                Log.e("RESPONSE", "Value: " + response.body()!!)
                if (response.body() != null) {
                    Thread(Runnable {
                        val clsFactsResponse = response.body()!!
                        val clsRootFact = ClsRootFact(clsFactsResponse.title)
                        factDao.deleteRootFact()
                        factDao.deleteAllFacts()
                        factDao.insertRootFactDetail(clsRootFact)
                        factDao.insertFactsList(clsFactsResponse.clsFacts)
                    }).start()
                } else {
                    listener.onFailure()
                }

            }

        })

    }

    fun getTotalResponseObjFromDB(): LiveData<List<ClsFacts>> {
        return factDao.getAllFactTableData()
    }

    fun getRootFactsData(): LiveData<ClsRootFact> {
        return factDao.getRootFactData()
    }


    /**
     * interface to give the CallBacks on the Response from Server.
     */
    interface CallBackListener {
        fun onSuccess(data: ClsFactsResponse)
        fun onFailure()
    }
}