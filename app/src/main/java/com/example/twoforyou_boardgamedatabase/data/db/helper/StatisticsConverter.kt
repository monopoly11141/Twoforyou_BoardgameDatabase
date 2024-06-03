package com.example.twoforyou_boardgamedatabase.data.db.helper

import androidx.room.TypeConverter
import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.example.twoforyou_boardgamedatabase.data.model.Statistics
import com.google.gson.Gson

class StatisticsConverter {
    @TypeConverter
    fun statisticsToJson(value: Statistics): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToStatistics(value: String): Statistics? {
        return Gson().fromJson(value, Statistics::class.java)
    }
}