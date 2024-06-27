package com.example.twoforyou_boardgamedatabase.ui.filtered

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.domain.FilteredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilteredViewModel @Inject constructor(
    private val repository: FilteredRepository
) : ViewModel() {
    private val _state = MutableStateFlow(FilteredUiState())
    val state = _state.asStateFlow()

    fun updateBoardgameList(idList: List<Int>) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    boardgameList = repository.getBoardgameListByIdList(idList)
                        .stateIn(viewModelScope).value
                )
            }
        }
    }

}