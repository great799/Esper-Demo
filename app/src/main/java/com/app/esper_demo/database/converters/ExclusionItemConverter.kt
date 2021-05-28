package com.app.esper_demo.database.converters

import androidx.room.TypeConverter
import com.app.esper_demo.network.model.ExclusionItem
import com.google.gson.Gson

class ExclusionItemConverter {
    @TypeConverter
    fun listToJson(value: List<ExclusionItem>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<ExclusionItem>::class.java).toList()
}