package com.superpromo.superpromo.data.db.dao

import androidx.room.*
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import kotlinx.coroutines.flow.Flow


@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ShoppingListDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ShoppingListDb>)

    @Update
    fun update(item: ShoppingListDb)

    @Update
    fun updateAll(list: List<ShoppingListDb>)

    @Query("DELETE FROM shopping_lists WHERE id = :itemId")
    fun delete(itemId: Long)

    @Query("DELETE FROM shopping_lists")
    fun deleteAll()

    @Query("SELECT * from shopping_lists WHERE id = :itemId")
    fun get(itemId: Long): ShoppingListDb

    @Query("SELECT * FROM shopping_lists WHERE isArchived IS 0 ORDER BY created DESC")
    fun getAll(): Flow<List<ShoppingListDb>>

    @Query("SELECT * FROM shopping_lists WHERE isArchived IS 1 ORDER BY created DESC")
    fun getAllArchived(): Flow<List<ShoppingListDb>>
}
