package com.superpromo.superpromo.repository.card

import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.di.DefaultDispatcher
import com.superpromo.superpromo.di.IoDispatcher
import com.superpromo.superpromo.repository.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val superPromoDb: SuperPromoDb,
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseRepository(ioDispatcher), CardRepository {

    override suspend fun insert(card: CardDb) {
        withContext(ioDispatcher) {
            superPromoDb.cardDao().insert(card)
        }
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            superPromoDb.cardDao().delete(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(ioDispatcher) {
            superPromoDb.cardDao().deleteAll()
        }
    }

    override fun getList(): Flow<List<CardDb>> =
        superPromoDb.cardDao().getAll()
            .distinctUntilChanged()
            .flowOn(ioDispatcher)
}
