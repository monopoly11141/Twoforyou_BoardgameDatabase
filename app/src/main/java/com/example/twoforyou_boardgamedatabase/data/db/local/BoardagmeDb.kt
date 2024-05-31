package com.example.twoforyou_boardgamedatabase.data.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem

@Database(
    entities =[BoardgameItem::class],
    version = 1,
    exportSchema = false
)
abstract class BoardagmeDb : RoomDatabase(){
    abstract val boardgameDao: BoardgameDao
}