package com.superpromo.superpromo.di

import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.network.SuperPromoApi
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
    fun provideRepository(
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
}