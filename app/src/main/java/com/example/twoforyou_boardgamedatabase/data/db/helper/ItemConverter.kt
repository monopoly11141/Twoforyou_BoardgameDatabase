package com.example.twoforyou_boardgamedatabase.data.db.helper

import androidx.room.TypeConverter
import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.google.gson.Gson

class ItemConverter {
    @TypeConverter
    fun itemToJson(value: Item): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToItem(value: String): Item? {
        return Gson().fromJson(value, Item::class.java)
    }
}