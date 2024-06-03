package com.example.twoforyou_boardgamedatabase.data.db.helper

import androidx.room.TypeConverter
import com.example.twoforyou_boardgamedatabase.data.model.Link
import com.google.gson.Gson

class LinkListConverter {
    @TypeConverter
    fun listToJson(value: List<Link>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Link>? {
        return Gson().fromJson(value, Array<Link>::class.java)?.toList()
    }
}