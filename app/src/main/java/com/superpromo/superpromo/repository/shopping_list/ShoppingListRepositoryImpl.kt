package com.superpromo.superpromo.repository.shopping_list

import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.db.model.ShoppingListDb
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

class ShoppingListRepositoryImpl @Inject constructor(
    private val superPromoDb: SuperPromoDb,
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseRepository(ioDispatcher), ShoppingListRepository {

    override suspend fun insert(card: ShoppingListDb) {
        withContext(ioDispatcher) {
            superPromoDb.shoppingListDao().insert(card)
        }
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            superPromoDb.shoppingListDao().delete(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(ioDispatcher) {
            superPromoDb.shoppingListDao().deleteAll()
        }
    }

    override suspend fun get(id: Long): ShoppingListDb {
        return withContext(ioDispatcher) {
            superPromoDb.shoppingListDao().get(id)
        }
    }

    override fun getAll(): Flow<List<ShoppingListDb>> =
        superPromoDb.shoppingListDao().getAll()
            .distinctUntilChanged()
            .flowOn(ioDispatcher)

    override fun getAllArchived(): Flow<List<ShoppingListDb>> =
        superPromoDb.shoppingListDao().getAllArchived()
            .distinctUntilChanged()
            .flowOn(ioDispatcher)
}
