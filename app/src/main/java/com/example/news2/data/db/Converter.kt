package com.example.news2.data.db

import androidx.room.TypeConverter
import com.example.news2.domain.model.responce.Source

class Converter {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}