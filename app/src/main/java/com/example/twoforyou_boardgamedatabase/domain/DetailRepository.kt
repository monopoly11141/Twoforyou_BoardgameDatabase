package com.example.twoforyou_boardgamedatabase.domain

import com.example.twoforyou_boardgamedatabase.data.remote.BoardgamegeekApi
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Retrofit

interface DetailRepository {

    val htmlString : StateFlow<String>
    fun getBoardgamegeekApi(): BoardgamegeekApi
    fun updateHtmlString()
}