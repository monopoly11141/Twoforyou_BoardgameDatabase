package com.example.twoforyou_boardgamedatabase.domain

import android.service.carrier.CarrierMessagingService.ResultCallback
import com.example.twoforyou_boardgamedatabase.data.db.remote.BoardgamegeekApi
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.data.model.Items
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DisplayRepository {
    fun getBoardgamegeekApi(): BoardgamegeekApi

    suspend fun insertItemsToDb(boardgameItem: BoardgameItem)
    suspend fun deleteBoardgameItem(boardgameItem: BoardgameItem)

    fun getBoardgameItem(id: Int, callback: ResultCallback<BoardgameItem>)
    fun getAllBoardgameItem(): Flow<List<BoardgameItem>>
}