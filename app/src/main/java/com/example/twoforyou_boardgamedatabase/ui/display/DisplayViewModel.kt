package com.example.twoforyou_boardgamedatabase.ui.display

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel @Inject constructor(
    private val repository: DisplayRepository
) : ViewModel() {

    var keyword = mutableStateOf("")
    private val _state = MutableStateFlow(DisplayUiState())
    val state = combine(
        repository.getAllBoardgameItem(),
        repository.getBoardgameFromKeyword(keyword.value),
        _state
    ) { array ->
        DisplayUiState(
            boardgameItemList = array[0] as List<BoardgameItem>,
            searchedBoardgameItemList = array[1] as List<BoardgameItem>
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

    fun searchBoardgame(searchQuery: String): Flow<List<BoardgameItem>> {
        return repository.getBoardgameFromKeyword(searchQuery)

    }

}
