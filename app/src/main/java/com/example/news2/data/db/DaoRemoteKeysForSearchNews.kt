package com.example.news2.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news2.domain.model.entity.RemoteKeysForSearchNews

@Dao
interface DaoRemoteKeysForSearchNews {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysForSearchNews>)

    @Query("SELECT * FROM remote_search_news WHERE articleId = :articleId")
    suspend fun remoteKeysRepoId(articleId: Long): RemoteKeysForSearchNews?

    @Query("DELETE FROM remote_search_news")
    suspend fun clearRemoteKeys()
}