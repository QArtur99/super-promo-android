package com.superpromo.superpromo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.superpromo.superpromo.data.db.model.ProductDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ProductDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ProductDb>)

    @Update
    fun update(item: ProductDb)

    @Update
    fun updateAll(list: List<ProductDb>)

    @Query("DELETE FROM products WHERE id = :itemId")
    fun delete(itemId: Long)

    @Query("DELETE FROM products")
    fun deleteAll()

    @Query("SELECT * from products WHERE id = :itemId")
    fun getItem(itemId: Long): ProductDb

    @Query("SELECT * FROM products WHERE shoppingListId = :shoppingListId ORDER BY id DESC")
    fun getAll(shoppingListId: Long): Flow<List<ProductDb>>
}
