package com.example.twoforyou_boardgamedatabase.data.db.remote

import com.example.twoforyou_boardgamedatabase.data.model.api_model.Items
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardgamegeekApi {
    @GET("xmlapi2/thing?stats=1&")
    fun boardListPost(@Query("id") id: String): Call<Items>
}