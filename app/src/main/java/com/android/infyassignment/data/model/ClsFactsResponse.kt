package com.android.infyassignment.data.model

import com.google.gson.annotations.SerializedName

data class ClsFactsResponse(
    @SerializedName("rows")
    val clsFacts: List<ClsFacts>,
    val title: String
){
    override fun toString(): String {
        return "ClsFactsResponse(clsFacts=$clsFacts, title='$title')"
    }
}
