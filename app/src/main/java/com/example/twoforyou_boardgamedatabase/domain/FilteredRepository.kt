package com.example.twoforyou_boardgamedatabase.domain

import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import kotlinx.coroutines.flow.Flow

interface FilteredRepository {
    fun getBoardgameListByIdList(idList: List<Int>) : Flow<List<BoardgameItem>>

}