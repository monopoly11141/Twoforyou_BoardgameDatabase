package com.example.twoforyou_boardgamedatabase.ui.display

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.data.db.local.BoardgameDb
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.data.model.Name
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    var searchString by mutableStateOf("")

    private val _state = MutableStateFlow(DisplayUiState())
    val state = combine(
        repository.getAllBoardgameItem(),
        repository.getNameMatches(searchString),
        _state
    ) { array ->
        DisplayUiState(
            boardgameItemList = array[0] as List<BoardgameItem>,
            searchedBoardgameItemList = array[1] as List<BoardgameItem>
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)


    fun insertItems(url: String) : Boolean {
        var id = -1
        try {
            id = url.substringAfter("boardgame/").substringBefore("/").toInt()
        } catch(e: Exception) {
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

    fun pickName(nameList: List<String>) : String {
        pickKoreanName(nameList)?.let { index ->
            return nameList[index]
        }
        return nameList[0]
    }

    fun searchForBoardgame(searchString: String) {
        this.searchString = searchString
        viewModelScope.launch {
            _state.update {
                it.copy(
                    searchedBoardgameItemList = repository.getNameMatches(searchString).flattenToList()
                )
            }
        }
    }

    private fun pickKoreanName(nameList: List<String>) : Int? {
        for(i in nameList.indices) {
            val koreanName = getKoreanName(nameList[i])
            if(koreanName.isNotEmpty()) {
                return i
            }
        }
        return null
    }

    private fun getKoreanName(nameString: String) : String {
        return nameString.filter {
            "^[ㄱ-ㅎ가-힣]*$".toRegex().containsMatchIn(it.toString())
        }.replace("_", " ").trim()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

}
