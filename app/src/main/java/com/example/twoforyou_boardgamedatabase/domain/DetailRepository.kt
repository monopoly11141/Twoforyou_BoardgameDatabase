package com.example.twoforyou_boardgamedatabase.domain

import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.example.twoforyou_boardgamedatabase.data.model.Items
import com.example.twoforyou_boardgamedatabase.data.remote.BoardgamegeekApi
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Retrofit

interface DetailRepository {
    val items : StateFlow<Items>
    fun getBoardgamegeekApi(): BoardgamegeekApi
    fun updateItems()
}