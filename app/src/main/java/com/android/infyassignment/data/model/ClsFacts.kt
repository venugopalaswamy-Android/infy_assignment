package com.android.infyassignment.data.model

import androidx.room.*
import com.android.infyassignment.utilities.COLUMN_INDEX_VALUE

@Entity(tableName = "tbl_facts")
data class ClsFacts(
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "imageHref") var imageHref: String?,
    @ColumnInfo(name = "title") var title: String?
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "factId")
    var factId: Long = 0L

    @ColumnInfo(name = "root_fact_id")
    var root_fact_id: Long = COLUMN_INDEX_VALUE

    override fun toString(): String {
        return "ClsFacts(description='$description', imageHref='$imageHref', title='$title')"
    }
}