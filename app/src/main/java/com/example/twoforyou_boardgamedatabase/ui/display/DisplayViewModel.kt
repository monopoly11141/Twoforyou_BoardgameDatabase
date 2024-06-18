package com.example.twoforyou_boardgamedatabase.ui.display

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel @Inject constructor(
    private val repository: DisplayRepository
) : ViewModel() {
    lateinit var a : List<BoardgameItem>
    var searchedBoardgame by mutableStateOf(emptyList<BoardgameItem>())
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
                repository.insertItemToDb(boardgameItem = it)

            }
        })
        return true
    }

    fun updateBoardgameItemFromApi(boardgameItem: BoardgameItem) : Boolean {
        Log.d("TAG", "updateBoardgameItemFromApi: ${boardgameItem}")
        val url = boardgameItem.boardgameUrl
        var id = -1
        try {
            id = url.substringAfter("boardgame/").substringBefore("/").toInt()
        } catch (e: Exception) {
            return false
        }

        repository.getBoardgameItem(id, callback = {
            boardgameItem.ranking = it.ranking
            boardgameItem.bayesAverageValue = it.bayesAverageValue
            boardgameItem.averageValue = it.averageValue
            boardgameItem.numUsersRated = it.numUsersRated
            boardgameItem.minPlayTimeValue = it.minPlayTimeValue
            boardgameItem.maxPlayersValue = it.maxPlayersValue
            boardgameItem.linkValueList = it.linkValueList
            viewModelScope.launch {
                repository.updateBoardgameItem(boardgameItem = boardgameItem)

            }
        })
        viewModelScope.launch {
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem().stateIn(viewModelScope).value
                )
            }
        }

        return true
    }

    fun deleteBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.deleteBoardgameItem(boardgameItem)
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem().stateIn(viewModelScope).value
                )
            }
        }
    }

    fun editBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.updateBoardgameItem(boardgameItem)
        }
    }

    fun searchBoardgame(searchQuery: String) {
        viewModelScope.launch {
            searchedBoardgame = repository.getBoardgameFromKeyword(searchQuery).stateIn(viewModelScope).value
        }

    }
}
