package com.superpromo.superpromo.di

import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.repository.card.CardRepository
import com.superpromo.superpromo.repository.card.CardRepositoryImpl
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.repository.main.SuperPromoRepositoryImpl
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.product.ProductRepositoryImpl
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideShopRepository(
        superPromoDb: SuperPromoDb,
        superPromoApi: SuperPromoApi,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SuperPromoRepository {
        return SuperPromoRepositoryImpl(
            superPromoDb,
            superPromoApi,
            defaultDispatcher,
            ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideCardRepository(
        superPromoDb: SuperPromoDb,
        superPromoApi: SuperPromoApi,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CardRepository {
        return CardRepositoryImpl(
            superPromoDb,
            superPromoApi,
            defaultDispatcher,
            ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideShopListRepository(
        superPromoDb: SuperPromoDb,
        superPromoApi: SuperPromoApi,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ShoppingListRepository {
        return ShoppingListRepositoryImpl(
            superPromoDb,
            superPromoApi,
            defaultDispatcher,
            ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        superPromoDb: SuperPromoDb,
        superPromoApi: SuperPromoApi,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ProductRepository {
        return ProductRepositoryImpl(
            superPromoDb,
            superPromoApi,
            defaultDispatcher,
            ioDispatcher
        )
    }
}
