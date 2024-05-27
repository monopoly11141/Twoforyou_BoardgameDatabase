package com.example.twoforyou_boardgamedatabase.ui.detail

import com.example.twoforyou_boardgamedatabase.data.model.Item
import com.example.twoforyou_boardgamedatabase.data.model.Items
import com.example.twoforyou_boardgamedatabase.data.model.Ranks
import com.example.twoforyou_boardgamedatabase.data.model.Ratings
import com.example.twoforyou_boardgamedatabase.data.model.Statistics

data class DetailUiState(
    val items: Items = Items(
        Item(
          ""
        )
    )
)