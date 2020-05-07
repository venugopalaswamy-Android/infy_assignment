package com.android.infyassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.data.model.ClsFactsResponse
import com.android.infyassignment.data.model.ClsRootFact
import com.android.infyassignment.utilities.DataConvertor

@Database(entities = [ClsRootFact::class,ClsFacts::class], version = 1)
@TypeConverters(DataConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFactDao(): FactDao
}