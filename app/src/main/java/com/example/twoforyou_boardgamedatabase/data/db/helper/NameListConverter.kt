package com.example.twoforyou_boardgamedatabase.data.db.helper

import androidx.room.TypeConverter
import com.example.twoforyou_boardgamedatabase.data.model.Link
import com.example.twoforyou_boardgamedatabase.data.model.Name
import com.google.gson.Gson

class NameListConverter {
    @TypeConverter
    fun listToJson(value: List<Name>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Name>? {
        return Gson().fromJson(value, Array<Name>::class.java)?.toList()
    }
}