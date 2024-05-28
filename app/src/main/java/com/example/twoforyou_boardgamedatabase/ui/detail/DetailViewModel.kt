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

    private val _state = MutableStateFlow(DetailUiState())
    val state = combine(
        repository.items,
        _state
    ) { array ->
        DetailUiState(
            items = array[0] as Items
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun updateItems() {
        viewModelScope.launch {
            repository.updateItems()
        }
        _state.update {
            it.copy(
                items = repository.items.value
            )
        }
    }
}