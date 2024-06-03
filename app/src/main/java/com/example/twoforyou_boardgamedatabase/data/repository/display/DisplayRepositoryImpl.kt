package com.example.twoforyou_boardgamedatabase.data.repository.display

import android.content.ContentValues.TAG
import android.service.carrier.CarrierMessagingService.ResultCallback
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.twoforyou_boardgamedatabase.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedatabase.data.db.local.BoardgameDb
import com.example.twoforyou_boardgamedatabase.data.db.remote.BoardgamegeekApi
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.data.model.Items
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.internal.notify
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class DisplayRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val boardgameDao: BoardgameDao
) : DisplayRepository {

    override fun getBoardgamegeekApi(): BoardgamegeekApi {
        return retrofit.create(BoardgamegeekApi::class.java)
    }

    override suspend fun insertItemsToDb(boardgameItem: BoardgameItem) {
        boardgameDao.insertBoardgame(boardgameItem)
    }

    override suspend fun deleteBoardgameItem(boardgameItem: BoardgameItem) {
        boardgameDao.deleteBoardgame(boardgameItem)
    }

    override fun getBoardgameItem(id: Int, callback: ResultCallback<BoardgameItem>) {
        val boardgameItem = BoardgameItem()
        val call = getBoardgamegeekApi().boardListPost(id.toString())
        var items : Items
        call.enqueue(object : Callback<Items> {
            override fun onResponse(
                call: Call<Items>,
                response: Response<Items>
            ) {
                if(response.isSuccessful) {
                    try {
                        Log.d(TAG, "onResponse: successful response, ${response.body()!!.item}")
                        items = response.body()!!
                        boardgameItem.item = items.item
                        callback.onReceiveResult(boardgameItem)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(p0: Call<Items>,  p1: Throwable) {
                p1.printStackTrace()
            }
        })

    }

    override fun getAllBoardgameItem(): Flow<List<BoardgameItem>> {
        return boardgameDao.getAllBoardgame()
    }

}