package com.android.infyassignment.data.network

import com.android.infyassignment.data.model.ClsFactsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {
    @GET("s/{keyStrValue}/facts.json")
    fun getFactsFromServer(@Path("keyStrValue") value: String): Call<ClsFactsResponse>
}