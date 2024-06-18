package com.example.twoforyou_boardgamedatabase.domain

import android.service.carrier.CarrierMessagingService.ResultCallback
import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.example.twoforyou_boardgamedatabase.data.model.Items
import com.example.twoforyou_boardgamedatabase.data.db.remote.BoardgamegeekApi
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Retrofit

interface DetailRepository {
    suspend fun getBoardgameItemByRoomKey(id: Int) : BoardgameItem

}