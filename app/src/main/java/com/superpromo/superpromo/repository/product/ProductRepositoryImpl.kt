package com.superpromo.superpromo.repository.product

import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.data.db.model.ProductDb
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

class ProductRepositoryImpl @Inject constructor(
    private val superPromoDb: SuperPromoDb,
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseRepository(ioDispatcher), ProductRepository {

    override suspend fun insert(card: ProductDb) {
        withContext(ioDispatcher) {
            superPromoDb.productDao().insert(card)
        }
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            superPromoDb.productDao().delete(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(ioDispatcher) {
            superPromoDb.productDao().deleteAll()
        }
    }

    override fun getList(shoppingListId: Long): Flow<List<ProductDb>> =
        superPromoDb.productDao().getAll(shoppingListId)
            .distinctUntilChanged()
            .flowOn(ioDispatcher)

}