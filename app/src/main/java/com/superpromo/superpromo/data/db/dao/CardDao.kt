package com.superpromo.superpromo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.superpromo.superpromo.data.db.model.CardDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: CardDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CardDb>)

    @Update
    fun update(item: CardDb)

    @Update
    fun updateAll(list: List<CardDb>)

    @Query("DELETE FROM cards WHERE id = :itemId")
    fun delete(itemId: Long)

    @Query("DELETE FROM cards")
    fun deleteAll()

    @Query("SELECT * from cards WHERE id = :itemId")
    fun getItem(itemId: Long): CardDb

    @Query("SELECT * FROM cards ORDER BY id DESC")
    fun getAll(): Flow<List<CardDb>>
}
