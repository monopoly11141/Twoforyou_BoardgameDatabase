package com.example.twoforyou_boardgamedatabase.ui.detail

import android.renderscript.Script
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.example.twoforyou_boardgamedatabase.data.model.Items
import com.example.twoforyou_boardgamedatabase.data.remote.BoardgamegeekApi
import com.example.twoforyou_boardgamedatabase.domain.DetailRepository
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {
    private lateinit var items: Items

    private val _state = MutableStateFlow(DetailUiState())
    val state = combine(
        repository.items,
        _state
    ) { array ->
        DetailUiState(
            items = array[0] as Items
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun updateItems() : Items{
        viewModelScope.launch {
            items = repository.updateItems()
        }
        _state.update {
            it.copy(
                items = repository.items.value
            )
        }
        return items
    }
//
//    fun updateHtmlString() {
//        viewModelScope.launch {
//            repository.updateHtmlString()
//        }
//        _state.update {
//            it.copy(
//                htmlString = repository.htmlString.value
//            )
//        }
//    }
//
//    fun xmlToListParser(xmlString: String) : String {
//        val xmlMapper = XmlMapper()
//        val stringList = mutableListOf<String>()
//        try {
//            stringList.add(xmlMapper.readValue(xmlString, stringList.javaClass).toString())
//
//        }catch(e: Exception) {
//            e.printStackTrace()
//        }
//        return stringList.joinToString()
//    }

    suspend fun getItems(): Item {
        return repository.getBoardgamegeekApi().boardListPost("224517").execute().body()!!.item
    }
}