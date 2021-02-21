package com.superpromo.superpromo.repository.shopping_list

import com.superpromo.superpromo.data.db.model.ShoppingListDb
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun insert(card: ShoppingListDb)
    suspend fun delete(id: Long)
    suspend fun deleteAll()
    fun getAll(): Flow<List<ShoppingListDb>>
    fun getAllArchived(): Flow<List<ShoppingListDb>>
}