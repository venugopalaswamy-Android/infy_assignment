package com.android.infyassignment.data.model

import androidx.room.*

@Entity(tableName = "tbl_facts")
data class ClsFacts(
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "imageHref") var imageHref: String?,
    @ColumnInfo(name = "title") var title: String?
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "factId")
    var factId: Long = 0L

    override fun toString(): String {
        return "ClsFacts(description='$description', imageHref='$imageHref', title='$title')"
    }
}