package com.superpromo.superpromo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superpromo.superpromo.data.db.dao.CardDao
import com.superpromo.superpromo.data.db.dao.ProductDao
import com.superpromo.superpromo.data.db.dao.ShopDao
import com.superpromo.superpromo.data.db.dao.ShoppingListDao
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.data.db.model.ShopDb
import com.superpromo.superpromo.data.db.model.ShoppingListDb

@Database(
    entities = [
        ShopDb::class,
        CardDb::class,
        ShoppingListDb::class,
        ProductDb::class,
    ], version = 4, exportSchema = true
)
abstract class SuperPromoDb : RoomDatabase() {

    abstract fun shopDao(): ShopDao
    abstract fun cardDao(): CardDao
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun productDao(): ProductDao

}