package com.superpromo.superpromo.repository.card

import com.superpromo.superpromo.data.db.model.CardDb
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun insertCard(card: CardDb)
    suspend fun deleteCard(id: Long)
    suspend fun deleteCardAll()
    fun getCardList(): Flow<List<CardDb>>
}