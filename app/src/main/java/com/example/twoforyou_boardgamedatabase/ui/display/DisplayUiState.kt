package com.example.twoforyou_boardgamedatabase.ui.display

import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem

data class DisplayUiState(
    val boardgameItemList: List<BoardgameItem> = emptyList()
)