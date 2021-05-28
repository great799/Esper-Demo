package com.app.esper_demo.database.converters

import androidx.room.TypeConverter
import com.app.esper_demo.network.model.OptionDetail
import com.google.gson.Gson

class OptionDetailConverter {
    @TypeConverter
    fun listToJson(value: List<OptionDetail>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<OptionDetail>::class.java).toList()
}