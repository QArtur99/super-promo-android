package com.superpromo.superpromo.di

import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.repository.card.CardRepository
import com.superpromo.superpromo.repository.card.CardRepositoryImpl
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.repository.main.SuperPromoRepositoryImpl
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
}