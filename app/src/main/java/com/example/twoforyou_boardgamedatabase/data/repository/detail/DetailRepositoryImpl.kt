package com.example.twoforyou_boardgamedatabase.data.repository.detail

import android.content.ContentValues.TAG
import android.media.Rating
import android.util.Log
import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.example.twoforyou_boardgamedatabase.data.model.Items
import com.example.twoforyou_boardgamedatabase.data.model.Link
import com.example.twoforyou_boardgamedatabase.data.model.Ranks
import com.example.twoforyou_boardgamedatabase.data.model.Ratings
import com.example.twoforyou_boardgamedatabase.data.model.Statistics
import com.example.twoforyou_boardgamedatabase.data.remote.BoardgamegeekApi
import com.example.twoforyou_boardgamedatabase.domain.DetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Thread.State
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : DetailRepository {

    //private val _items = MutableStateFlow(Items(Item("")))
    private val _items = MutableStateFlow(Items(Item("",listOf(Link()), Statistics())))
    override val items: StateFlow<Items>
        get() = _items.asStateFlow()
    override fun getBoardgamegeekApi(): BoardgamegeekApi {
        return retrofit.create(BoardgamegeekApi::class.java)
    }

    override fun updateItems() {
        val call = getBoardgamegeekApi().boardListPost("224517")
        call.enqueue(object : Callback<Items> {
            override fun onResponse(
                call: Call<Items>,
                response: Response<Items>
            ) {
                if(response.isSuccessful) {
                    try {
                        Log.d(TAG, "onResponse: successful response, ${response.body()!!.item}")
                        _items.value = response.body()!!
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }
                }
            }

            override fun onFailure(p0: Call<Items>, p1: Throwable) {
                Log.d(TAG, "onResponse onFailure: operation failed with item: ${p0.toString()}")
                p1.printStackTrace()
            }
        })
    }
}