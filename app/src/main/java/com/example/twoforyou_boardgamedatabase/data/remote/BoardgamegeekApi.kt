package com.example.twoforyou_boardgamedatabase.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BoardgamegeekApi {
    @POST("xmlapi2/thing?stats=1&")
    fun boardListPost(@Query("id") id: String): Call<ResponseBody>
}