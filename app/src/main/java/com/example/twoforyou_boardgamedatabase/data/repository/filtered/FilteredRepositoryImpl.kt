package com.example.twoforyou_boardgamedatabase.data.repository.filtered

import com.example.twoforyou_boardgamedatabase.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedatabase.domain.FilteredRepository
import javax.inject.Inject

class FilteredRepositoryImpl @Inject constructor(
    private val boardgameDao: BoardgameDao
) : FilteredRepository {
}