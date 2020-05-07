package com.android.infyassignment.utilities

import androidx.room.TypeConverter
import com.android.infyassignment.ApplicationLevel
import com.android.infyassignment.data.model.ClsFacts
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class DataConvertor: Serializable {

    @TypeConverter
    fun  fromFactsListToString(list: List<ClsFacts>): String{
        if(list.isEmpty()){
            return ""
        }
        val turnsType = object : TypeToken<List<ClsFacts>>() {}.type
        return  Gson().toJson(list, turnsType)
    }


    @TypeConverter
    fun fromStrToList(strValue: String):List<ClsFacts>{
        if(ApplicationLevel.isNullOrEmpty(strValue)){
            return  listOf()
        }
        val turnsType = object : TypeToken<List<ClsFacts>>() {}.type
        return Gson().fromJson<List<ClsFacts>>(strValue,turnsType)
    }
}