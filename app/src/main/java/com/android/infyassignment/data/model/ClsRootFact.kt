package com.android.infyassignment.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.infyassignment.utilities.COLUMN_INDEX_VALUE

@Entity(tableName = "tbl_root")
data class ClsRootFact(
    @ColumnInfo(name = "title") val title: String
) {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "root_id")
    var rootId: Long = COLUMN_INDEX_VALUE

}