package com.superpromo.superpromo.di

import android.content.Context
import androidx.room.Room
import com.superpromo.superpromo.data.db.SuperPromoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun getInstance(context: Context): SuperPromoDb {
        return Room.databaseBuilder(
            context.applicationContext,
            SuperPromoDb::class.java,
            "super_promo_database"
        )
            .fallbackToDestructiveMigration()

            .build()
    }

}