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
import com.example.twoforyou_boardgamedatabase.data.db.remote.BoardgamegeekApi
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

class DetailRepositoryImpl() : DetailRepository {

}