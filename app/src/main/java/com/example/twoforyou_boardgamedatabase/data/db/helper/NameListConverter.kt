package com.example.twoforyou_boardgamedatabase.data.db.helper

import androidx.room.TypeConverter
import com.example.twoforyou_boardgamedatabase.data.model.Link
import com.example.twoforyou_boardgamedatabase.data.model.Name
import com.google.gson.Gson

class NameListConverter {
    @TypeConverter
    fun listToJson(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value, Array<String>::class.java)?.toList()
    }
}