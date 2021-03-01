package com.superpromo.superpromo.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
            .addCallback(dbCallback)
            .fallbackToDestructiveMigration()
            .build()
    }

    private val dbCallback = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            db.run {
                execSQL(
                    """CREATE TRIGGER IF NOT EXISTS productCount AFTER INSERT ON products
                                FOR EACH ROW
                                BEGIN
                                  update shopping_lists 
                                    set productCount = (select count(*) from products where shoppingListId = new.shoppingListId) 
                                    where id = new.shoppingListId;                            
                                END;"""
                )
                execSQL(
                    """CREATE TRIGGER IF NOT EXISTS productCountActive AFTER INSERT ON products
                                FOR EACH ROW
                                BEGIN
                                  update shopping_lists 
                                    set productCountActive = (select count(*) from products where shoppingListId = new.shoppingListId and isSelected = 1) 
                                    where id = new.shoppingListId;
                                END;"""
                )
            }
        }
    }

}