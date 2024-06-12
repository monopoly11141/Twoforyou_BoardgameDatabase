package com.example.twoforyou_boardgamedatabase.ui.display

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel @Inject constructor(
    private val repository: DisplayRepository
) : ViewModel() {
    lateinit var a : List<BoardgameItem>
    var searchedBoardgame by mutableStateOf(emptyList<BoardgameItem>())
    var keyword by mutableStateOf("")
    private val _state = MutableStateFlow(DisplayUiState())
    val state = combine(
        repository.getAllBoardgameItem(),
        _state
    ) { array ->
        DisplayUiState(
            boardgameItemList = array[0] as List<BoardgameItem>,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun insertItems(url: String): Boolean {
        var id = -1
        try {
            id = url.substringAfter("boardgame/").substringBefore("/").toInt()
        } catch (e: Exception) {
            return false
        }

        repository.getBoardgameItem(id, callback = {
            viewModelScope.launch {
                repository.insertItemsToDb(boardgameItem = it)

            }
        })
        return true
    }

    fun deleteBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.deleteBoardgameItem(boardgameItem)

        }
    }

    fun editBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.updateBoardgameItem(boardgameItem)
        }
    }

    fun searchBoardgame(searchQuery: String) {
//        viewModelScope.launch {
//            keyword = searchQuery
//            a = repository.getBoardgameFromKeyword(searchQuery).stateIn(viewModelScope).value
//        }.invokeOnCompletion {
//            _state.update {
//                it.copy(
//                    searchedBoardgameItemList = a
//                )
//            }
//            Log.d(TAG, "searchBoardgame: ${searchQuery}")
//            Log.d(TAG, "searchBoardgame: ${a}")
//        }

        viewModelScope.launch {
            searchedBoardgame = repository.getBoardgameFromKeyword(searchQuery).stateIn(viewModelScope).value
        }


    }

}
