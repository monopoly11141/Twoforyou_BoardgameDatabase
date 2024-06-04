package com.example.twoforyou_boardgamedatabase.data.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twoforyou_boardgamedatabase.data.db.helper.LinkValueListConverter
import com.example.twoforyou_boardgamedatabase.data.db.helper.NameListConverter
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem

@Database(
    entities = [BoardgameItem::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(
    LinkValueListConverter::class,
//    NameListConverter::class
)
abstract class BoardgameDb : RoomDatabase() {
    abstract val boardgameDao: BoardgameDao
}