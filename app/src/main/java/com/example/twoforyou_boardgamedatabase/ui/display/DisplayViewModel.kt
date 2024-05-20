package com.example.twoforyou_boardgamedatabase.ui.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel @Inject constructor(
    private val repository: DisplayRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DisplayUiState())
    val state = combine(
//        repository.sampleString,
        _state
    ) { array ->
        DisplayUiState(
            //sampleString = array[0] as String
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

}