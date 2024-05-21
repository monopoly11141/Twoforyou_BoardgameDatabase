package com.example.twoforyou_boardgamedatabase.data.repository.detail

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
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : DetailRepository {

    private val _htmlString = MutableStateFlow("")
    override val htmlString: StateFlow<String>
        get() = _htmlString.asStateFlow()
    override fun getBoardgamegeekApi(): BoardgamegeekApi {
        return retrofit.create(BoardgamegeekApi::class.java)
    }

    override fun updateHtmlString(){
        val call = getBoardgamegeekApi().boardListPost("224517")
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.isSuccessful) {
                    try {
                        _htmlString.value = response.body()!!.string() // <- 여기에 결과가 들어있음
                    } catch (e: Exception) {

                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }
}