package com.android.infyassignment.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.data.model.ClsRootFact

@Dao
interface FactDao {

    @Transaction
    @Query("Select * From tbl_facts")
    fun getAllFactTableData(): LiveData<List<ClsFacts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertRootFactDetail(clsRootFact: ClsRootFact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertFactsList(listOfFacts: List<ClsFacts>)

    @Query("Select * From tbl_root where root_id == 0")
    fun getRootFactData(): LiveData<ClsRootFact>

    @Query("Delete from tbl_facts")
    fun deletAllFacts()

    @Query("Delete from tbl_root")
    fun deleteRootFact()


}