package com.superpromo.superpromo.data.db

import androidx.room.*
import com.superpromo.superpromo.data.db.model.CardDb


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
    fun delete(itemId: Int)

    @Query("DELETE FROM cards")
    fun deleteAll()

    @Query("SELECT * from cards WHERE id = :itemId")
    fun getItem(itemId: Int): CardDb

    @Query("SELECT * FROM cards ORDER BY id DESC")
    fun getItemAll(): List<CardDb>
}
