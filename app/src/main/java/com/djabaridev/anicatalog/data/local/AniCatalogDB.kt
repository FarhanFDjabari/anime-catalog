package com.djabaridev.anicatalog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.djabaridev.anicatalog.data.local.converters.AniCatalogTypeConverter
import com.djabaridev.anicatalog.data.local.dao.AnimeDao
import com.djabaridev.anicatalog.data.local.dao.MangaDao
import com.djabaridev.anicatalog.data.local.models.AnimeListItemEntry
import com.djabaridev.anicatalog.data.local.models.MangaListItemEntry

@TypeConverters(value = [AniCatalogTypeConverter::class])
@Database(
    entities = [
        AnimeListItemEntry::class,
        MangaListItemEntry::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AniCatalogDB : RoomDatabase() {
    abstract val animeDao: AnimeDao
    abstract val mangaDao: MangaDao

    companion object {
        const val DATABASE_NAME = "anicatalog_db"
    }
}