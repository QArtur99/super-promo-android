package com.superpromo.superpromo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.superpromo.superpromo.data.db.model.ShopDb

@Dao
interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ShopDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ShopDb>)

    @Update
    fun update(item: ShopDb)

    @Update
    fun updateAll(list: List<ShopDb>)

    @Query("DELETE FROM shops WHERE id = :itemId")
    fun delete(itemId: Int)

    @Query("DELETE FROM shops")
    fun deleteAll()

    @Query("SELECT * from shops WHERE id = :itemId")
    fun getItem(itemId: Int): ShopDb

    @Query("SELECT * FROM shops ORDER BY id DESC")
    fun getAll(): List<ShopDb>
}
