package com.superpromo.superpromo.repository.main.mapper

import com.superpromo.superpromo.data.db.model.ShopDb
import com.superpromo.superpromo.data.network.model.Shop

fun List<Shop>.asDbModel(): List<ShopDb> {
    return map { it.asDbModel() }
}

fun Shop.asDbModel(): ShopDb {
    return ShopDb(
        id = this.id,
        categoryId = this.categoryId,
        name = this.name,
        imgUrl = this.imgUrl,
        url = this.url,
        productCount = this.productCount,
        isAvailable = this.isAvailable,
        isAvailableInDb = this.isAvailableInDb,
    )
}

fun List<ShopDb>.asDomainModel(): List<Shop> {
    return map { it.asDomainModel() }
}

fun ShopDb.asDomainModel(): Shop {
    return Shop(
        id = this.id,
        categoryId = this.categoryId,
        name = this.name,
        imgUrl = this.imgUrl,
        url = this.url,
        productCount = this.productCount,
        isAvailable = this.isAvailable,
        isAvailableInDb = this.isAvailableInDb,
    )
}