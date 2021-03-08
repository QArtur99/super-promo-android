package com.superpromo.superpromo.repository.product

import com.superpromo.superpromo.data.db.model.ProductDb
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insert(card: ProductDb): Long
    suspend fun delete(id: Long)
    suspend fun deleteAll()
    fun getList(shoppingListId: Long): Flow<List<ProductDb>>
}
