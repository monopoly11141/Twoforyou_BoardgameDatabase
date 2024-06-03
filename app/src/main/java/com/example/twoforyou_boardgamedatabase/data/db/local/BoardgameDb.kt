package com.example.twoforyou_boardgamedatabase.data.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twoforyou_boardgamedatabase.data.db.helper.ItemConverter
import com.example.twoforyou_boardgamedatabase.data.db.helper.LinkListConverter
import com.example.twoforyou_boardgamedatabase.data.db.helper.NameListConverter
import com.example.twoforyou_boardgamedatabase.data.db.helper.StatisticsConverter
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem

@Database(
    entities =[BoardgameItem::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    ItemConverter::class,
    LinkListConverter::class,
    NameListConverter::class,
    StatisticsConverter::class
)
abstract class BoardgameDb : RoomDatabase(){
    abstract val boardgameDao: BoardgameDao
}