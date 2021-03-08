package com.superpromo.superpromo.repository.card

import com.superpromo.superpromo.data.db.model.CardDb
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun insert(card: CardDb)
    suspend fun delete(id: Long)
    suspend fun deleteAll()
    fun getList(): Flow<List<CardDb>>
}
