package com.superpromo.superpromo.repository.card

import com.superpromo.superpromo.data.db.model.CardDb

interface CardRepository {
    suspend fun insertCard(card: CardDb)
    suspend fun deleteCardAll()
    suspend fun getCardList(): List<CardDb>
}