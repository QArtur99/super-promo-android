package com.superpromo.superpromo.repository.card

import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.di.DefaultDispatcher
import com.superpromo.superpromo.di.IoDispatcher
import com.superpromo.superpromo.repository.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val superPromoDb: SuperPromoDb,
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseRepository(ioDispatcher), CardRepository {

    override suspend fun insertCard(card: CardDb) {
        withContext(ioDispatcher) {
            superPromoDb.cardDao().insert(card)
        }
    }

    override suspend fun deleteCardAll() {
        withContext(ioDispatcher) {
            superPromoDb.shopDao().deleteAll()
        }
    }

    override suspend fun getCardList(): List<CardDb> {
        return withContext(ioDispatcher) {
            superPromoDb.cardDao().getItemAll()
        }
    }

}