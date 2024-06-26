package com.example.twoforyou_boardgamedatabase.ui.display

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import com.example.twoforyou_boardgamedatabase.ui.display.util.DISPLAY_ORDER
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
    var entireBoardgameItemList by mutableStateOf(emptyList<BoardgameItem>())
    var displayingBoardgameItemList by mutableStateOf(emptyList<BoardgameItem>())
    var displayOrder by mutableStateOf(DISPLAY_ORDER.ALPHABETICAL)
    var bottomBarLabelText by mutableStateOf("전체")

    init {
        viewModelScope.launch {
            entireBoardgameItemList = repository.getAllBoardgameItem().stateIn(viewModelScope).value
        }
    }

    private val _state = MutableStateFlow(DisplayUiState())
    val state = combine(
        repository.getAllBoardgameItem(),
        _state
    ) { array ->
        @Suppress("UNCHECKED_CAST")
        DisplayUiState(
            boardgameItemList = array[0] as List<BoardgameItem>,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun insertBoardgameItemToDb(url: String): Boolean {
        var id = -1
        try {
            id = url.substringAfter("boardgame/").substringBefore("/").toInt()
        } catch (e: Exception) {
            return false
        }

        repository.getBoardgameItem(id, callback = {
            viewModelScope.launch {
                repository.insertItemToDb(boardgameItem = it)
                entireBoardgameItemList =
                    repository.getAllBoardgameItem().stateIn(viewModelScope).value
            }
        })
        return true
    }

    fun updateBoardgameItemFromApi(boardgameItem: BoardgameItem) {
        val url = boardgameItem.boardgameUrl
        var id = -1
        try {
            id = url.substringAfter("boardgame/").substringBefore("/").toInt()
        } catch (e: Exception) {
            return
        }

        repository.getBoardgameItem(id, callback = {
            boardgameItem.ranking = it.ranking
            boardgameItem.bayesAverageValue = it.bayesAverageValue
            boardgameItem.averageValue = it.averageValue
            boardgameItem.numUsersRated = it.numUsersRated
            boardgameItem.minPlayTimeValue = it.minPlayTimeValue
            boardgameItem.maxPlayersValue = it.maxPlayersValue
            boardgameItem.linkValueList = it.linkValueList
            boardgameItem.averageWeight = it.averageWeight

            viewModelScope.launch {
                repository.updateBoardgameItem(boardgameItem = boardgameItem)
            }
        })
        viewModelScope.launch {
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem()
                        .stateIn(viewModelScope).value
                )
            }
        }
    }

    fun deleteBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.deleteBoardgameItem(boardgameItem)
            _state.update {
                it.copy(
                    boardgameItemList = repository.getAllBoardgameItem()
                        .stateIn(viewModelScope).value
                )
            }
            entireBoardgameItemList = repository.getAllBoardgameItem().stateIn(viewModelScope).value

        }
    }

    fun editBoardgameItem(boardgameItem: BoardgameItem) {
        viewModelScope.launch {
            repository.updateBoardgameItem(boardgameItem)
        }
        viewModelScope.launch {
            entireBoardgameItemList = repository.getAllBoardgameItem().stateIn(viewModelScope).value
        }
    }

    fun updateDisplayingBoardgameItemList(searchQuery: String) {
        viewModelScope.launch {
            if (searchQuery.isBlank()) {
                displayingBoardgameItemList = entireBoardgameItemList
            } else {
                displayingBoardgameItemList =
                    repository.getBoardgameFromKeyword(searchQuery).stateIn(viewModelScope).value
            }

            when (displayOrder) {
                DISPLAY_ORDER.ALPHABETICAL -> {
                    bottomBarLabelText = "전체"
                }

                DISPLAY_ORDER.FAVORITE -> {
                    displayingBoardgameItemList =
                        displayingBoardgameItemList.filter { it.isFavorite }
                    bottomBarLabelText = "즐겨찾기"
                }

                DISPLAY_ORDER.NON_FAVORITE -> {
                    displayingBoardgameItemList =
                        displayingBoardgameItemList.filter { !it.isFavorite }
                    bottomBarLabelText = "즐겨찾기 제외"
                }

                DISPLAY_ORDER.RANKING -> {
                    displayingBoardgameItemList = displayingBoardgameItemList.sortedBy {
                        it.ranking
                    }
                    bottomBarLabelText = "전체"
                }

                DISPLAY_ORDER.WEIGHT -> {
                    displayingBoardgameItemList = displayingBoardgameItemList.sortedBy {
                        it.averageWeight
                    }
                    bottomBarLabelText = "전체"
                }
            }
        }
    }

    fun updateDisplayOrder(displayOrder: DISPLAY_ORDER) {
        this.displayOrder = displayOrder
    }

}
