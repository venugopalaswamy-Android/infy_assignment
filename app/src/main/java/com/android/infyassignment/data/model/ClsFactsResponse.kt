package com.android.infyassignment.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.android.infyassignment.utilities.DataConvertor
import com.android.infyassignment.utilities.COLUMN_INDEX_VALUE
import com.google.gson.annotations.SerializedName

data class ClsFactsResponse(
    @SerializedName("rows")
    var clsFacts: List<ClsFacts>,
    var title: String
) {
    override fun toString(): String {
        return "ClsFactsResponse(clsFacts=$clsFacts, title='$title')"
    }
}
