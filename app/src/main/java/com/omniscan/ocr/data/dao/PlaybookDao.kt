package com.omniscan.ocr.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.omniscan.ocr.data.model.PlaybookEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaybookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: PlaybookEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<PlaybookEntry>)

    @Update
    suspend fun update(entry: PlaybookEntry)

    @Delete
    suspend fun delete(entry: PlaybookEntry)

    @Query("SELECT * FROM playbook_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): PlaybookEntry?

    @Query("SELECT * FROM playbook_entries ORDER BY updatedAt DESC")
    fun getAllEntries(): Flow<List<PlaybookEntry>>

    @Query("SELECT * FROM playbook_entries WHERE category = :category ORDER BY updatedAt DESC")
    fun getEntriesByCategory(category: String): Flow<List<PlaybookEntry>>

    @Query("SELECT * FROM playbook_entries WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchEntries(query: String): Flow<List<PlaybookEntry>>

    @Query("SELECT * FROM playbook_entries WHERE importance >= :importance ORDER BY importance DESC, updatedAt DESC")
    fun getEntriesByImportance(importance: Int): Flow<List<PlaybookEntry>>

    @Query("SELECT COUNT(*) FROM playbook_entries")
    suspend fun getCount(): Int

    @Query("SELECT SUM(totalWords) FROM playbook_entries")
    suspend fun getTotalWords(): Int?

    @Query("SELECT * FROM playbook_entries ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestEntry(): PlaybookEntry?

    @Query("SELECT * FROM playbook_entries ORDER BY updatedAt DESC LIMIT :limit")
    fun getRecentEntries(limit: Int): Flow<List<PlaybookEntry>>

    @Query("DELETE FROM playbook_entries WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM playbook_entries")
    suspend fun deleteAll()
}
