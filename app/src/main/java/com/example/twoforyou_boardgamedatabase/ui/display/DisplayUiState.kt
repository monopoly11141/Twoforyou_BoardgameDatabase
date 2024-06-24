package com.example.twoforyou_boardgamedatabase.ui.display

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem

data class DisplayUiState(
    val boardgameItemList: List<BoardgameItem> = emptyList(),
)