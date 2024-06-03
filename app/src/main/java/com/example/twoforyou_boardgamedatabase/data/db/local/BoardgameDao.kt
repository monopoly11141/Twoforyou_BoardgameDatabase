package com.example.twoforyou_boardgamedatabase.data.db.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardgameDao {

    @Query("SELECT * FROM boardgame_database")
    fun getAllBoardgame() : Flow<List<BoardgameItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBoardgame(boardgame: BoardgameItem)

    @Delete()
    suspend fun deleteBoardgame(boardgame: BoardgameItem)

    @Query("DELETE FROM boardgame_database")
    suspend fun deleteAllScript()

    @Query("SELECT * FROM boardgame_database WHERE id = :id LIMIT 1")
    suspend fun getBoardgameById(id: Int): BoardgameItem
}