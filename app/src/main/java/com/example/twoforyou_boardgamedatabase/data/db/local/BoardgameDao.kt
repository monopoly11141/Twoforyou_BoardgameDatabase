package com.example.twoforyou_boardgamedatabase.data.db.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardgameDao {

    @Query("SELECT * FROM boardgame_database ORDER BY koreanName ASC, englishName ASC")
    fun getAllBoardgame(): Flow<List<BoardgameItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBoardgame(boardgame: BoardgameItem)

    @Delete()
    suspend fun deleteBoardgame(boardgame: BoardgameItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBoardgame(boardgame: BoardgameItem)

    @Query("DELETE FROM boardgame_database")
    suspend fun deleteAllScript()

    @Query("SELECT * FROM boardgame_database WHERE id = :id LIMIT 1")
    suspend fun getBoardgameById(id: Int): BoardgameItem

    @Query("SELECT * FROM boardgame_database WHERE englishName LIKE :keyword")
    fun getBoardgameFromKeyword(keyword: String): Flow<List<BoardgameItem>>

}