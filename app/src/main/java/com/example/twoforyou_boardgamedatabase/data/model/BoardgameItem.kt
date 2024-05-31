package com.example.twoforyou_boardgamedatabase.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boardgame_database")
data class BoardgameItem(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    val item: Item = Item()
)