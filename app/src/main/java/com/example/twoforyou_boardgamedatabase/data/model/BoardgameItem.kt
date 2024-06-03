package com.example.twoforyou_boardgamedatabase.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boardgame_database")
data class BoardgameItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var item: Item = Item()
)