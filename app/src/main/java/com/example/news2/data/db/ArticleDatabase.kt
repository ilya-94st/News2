package com.example.news2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news2.domain.model.entity.*

@Database(
    entities = [SearchNewsEntity::class, RemoteKeysForSearchNews::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getDaoSearchNews() : DaoSearchNews
    abstract fun remoteKeysDaoSearch(): DaoRemoteKeysForSearchNews
}
