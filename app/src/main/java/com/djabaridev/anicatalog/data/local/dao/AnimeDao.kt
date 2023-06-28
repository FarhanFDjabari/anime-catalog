package com.djabaridev.anicatalog.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime")
    fun getAllAnime(): Flow<List<AnimeListItemEntry>>

    @Query("SELECT * FROM anime WHERE id = :id LIMIT 1")
    fun getAnime(id: Int): Flow<AnimeListItemEntry?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeListItemEntry)

    @Delete
    suspend fun deleteAllAnime(anime: List<AnimeListItemEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnime(anime: List<AnimeListItemEntry>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAnimeIsFavorite(anime: AnimeListItemEntry)

    @Query("SELECT * FROM anime WHERE isFavorite = 1")
    fun getFavoriteAnime(): Flow<List<AnimeListItemEntry>>
}