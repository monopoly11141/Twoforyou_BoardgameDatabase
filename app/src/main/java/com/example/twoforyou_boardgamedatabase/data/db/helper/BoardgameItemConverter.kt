package com.example.twoforyou_boardgamedatabase.data.db.helper

import androidx.room.TypeConverter
import com.google.gson.Gson

class BoardgameItemConverter {

    @TypeConverter
    fun listToJson(value: List<FabledCharacter>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<FabledCharacter>? {
        return Gson().fromJson(value, Array<FabledCharacter>::class.java)?.toList()
    }
}