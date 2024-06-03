package com.example.twoforyou_boardgamedatabase.ui.display

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Snackbar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
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

    private val _state = MutableStateFlow(DisplayUiState())
    val state = combine(
        repository.getAllBoardgameItem(),
        _state
    ) { array ->
        DisplayUiState(
            boardgameItemList = array[0] as List<BoardgameItem>
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

}
