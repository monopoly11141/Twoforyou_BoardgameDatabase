package com.example.twoforyou_boardgamedatabase.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boardgame_database")
data class BoardgameItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: List<String> = emptyList(),
    var description: String = "",
    var imageUrl: String = "",
    var minPlayersValue : Int = 0,
    var maxPlayersValue : Int = 0,
    var minPlayTimeValue : Int = 0,
    var maxPlayTimeValue : Int = 0,
    var linkValueList : List<String> = emptyList(),
    var numUsersRated : Int = 0,
    var averageValue : Float = 0f,
    var bayesAverageValue : Float = 0f,
    var ranking : Int = 0
)