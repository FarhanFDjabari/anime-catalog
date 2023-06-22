package com.djabaridev.anicatalog.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.djabaridev.anicatalog.data.local.models.MangaListItemEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Query("SELECT * FROM manga")
    fun getAllManga(): Flow<List<MangaListItemEntry>>

    @Query("SELECT * FROM manga WHERE id = :id LIMIT 1")
    fun getManga(id: Int): Flow<MangaListItemEntry?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManga(manga: MangaListItemEntry)

    @Delete
    fun deleteAllManga(manga: List<MangaListItemEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllManga(manga: List<MangaListItemEntry>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMangaIsFavorite(manga: MangaListItemEntry)

    @Query("SELECT * FROM manga WHERE isFavorite = 1")
    fun getFavoriteManga(): Flow<List<MangaListItemEntry>>
}