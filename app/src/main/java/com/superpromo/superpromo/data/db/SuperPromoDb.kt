package com.superpromo.superpromo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.data.db.model.ShopDb

@Database(
    entities = [
        ShopDb::class,
        CardDb::class,
    ], version = 11, exportSchema = false
)
abstract class SuperPromoDb : RoomDatabase() {

    abstract fun shopDao(): ShopDao
    abstract fun cardDao(): CardDao

}