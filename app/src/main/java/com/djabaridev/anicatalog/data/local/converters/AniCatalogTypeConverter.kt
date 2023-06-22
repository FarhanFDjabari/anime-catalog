package com.djabaridev.anicatalog.data.local.converters

import androidx.room.TypeConverter

class AniCatalogTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }
}