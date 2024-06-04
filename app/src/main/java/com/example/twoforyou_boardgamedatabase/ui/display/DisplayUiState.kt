package com.example.twoforyou_boardgamedatabase.ui.display

import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.example.twoforyou_boardgamedatabase.data.model.Items
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DisplayUiState(
    var boardgameItemList: List<BoardgameItem> = emptyList(),
    val searchedBoardgameItemList: List<BoardgameItem> = emptyList()
)