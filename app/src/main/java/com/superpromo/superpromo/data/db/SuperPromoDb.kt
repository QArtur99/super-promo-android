package com.superpromo.superpromo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superpromo.superpromo.data.db.model.ShopDb

@Database(entities = [ShopDb::class], version = 5, exportSchema = false)
abstract class SuperPromoDb : RoomDatabase() {

    abstract fun movieDatabaseDao(): SuperPromoDao

}