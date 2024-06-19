package com.example.twoforyou_boardgamedatabase.domain

import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem

interface DetailRepository {
    suspend fun getBoardgameItemByRoomKey(id: Int) : BoardgameItem

}