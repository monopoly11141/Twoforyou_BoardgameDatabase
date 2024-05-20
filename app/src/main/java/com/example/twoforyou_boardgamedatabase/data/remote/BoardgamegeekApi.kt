package com.example.twoforyou_boardgamedatabase.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface BoardgamegeekApi {
    @FormUrlEncoded
    @POST("thing?id={id}")
    suspend fun boardListPost(@Path("id") id: String): Call<ResponseBody>
}