package com.example.twoforyou_boardgamedatabase.data.repository.detail

import com.example.twoforyou_boardgamedatabase.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.domain.DetailRepository

class DetailRepositoryImpl(
    private val boardgameDao: BoardgameDao
) : DetailRepository {
    override suspend fun getBoardgameItemByRoomKey(id: Int): BoardgameItem {
        return boardgameDao.getBoardgameById(id)
    }
}