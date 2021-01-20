package com.superpromo.superpromo.di

import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.repository.SuperPromoRepository
import com.superpromo.superpromo.repository.SuperPromoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.Executor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        superPromoApi: SuperPromoApi,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SuperPromoRepository {
        return SuperPromoRepositoryImpl(
            superPromoApi,
            defaultDispatcher,
            ioDispatcher
        )
    }
}