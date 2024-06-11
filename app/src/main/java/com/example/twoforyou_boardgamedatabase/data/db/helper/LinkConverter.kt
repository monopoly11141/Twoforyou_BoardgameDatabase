package com.example.twoforyou_boardgamedatabase.data.db.helper

import androidx.room.TypeConverter
import com.example.twoforyou_boardgamedatabase.data.model.Link
import com.google.gson.Gson

class LinkConverter {
    @TypeConverter
    fun linkToJson(value: Link?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToLink(value: String): Link? {
        return Gson().fromJson(value, Link::class.java)
    }
}