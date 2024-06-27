package com.example.twoforyou_boardgamedatabase.data.repository.filtered

import androidx.compose.runtime.referentialEqualityPolicy
import com.example.twoforyou_boardgamedatabase.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.domain.FilteredRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilteredRepositoryImpl @Inject constructor(
    private val boardgameDao: BoardgameDao
) : FilteredRepository {
    override fun getBoardgameListByIdList(idList: List<Int>): Flow<List<BoardgameItem>> {
        return boardgameDao.getBoardgameListByIdList(idList)
    }
}