package com.example.twoforyou_boardgamedatabase.ui.filtered

import androidx.collection.emptyLongList
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem

data class FilteredUiState(
    val boardgameList : List<BoardgameItem> = emptyList<BoardgameItem>()
)