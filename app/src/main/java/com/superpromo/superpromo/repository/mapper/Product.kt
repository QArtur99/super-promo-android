package com.superpromo.superpromo.repository.mapper

import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.data.network.model.Product

fun List<Product>.asDbModel(): List<ProductDb> {
    return map { it.asDbModel() }
}

fun Product.asDbModel(): ProductDb {
    return ProductDb(
        productId = this.id,
        shoppingListId = 0,
        shopName = this.shopName,
        name = this.name,
        subtitle = this.subtitle,
        price = this.price,
        amount = this.amount,
        details = this.details,
        promoInfo = this.promoInfo,
        promo = this.promo,
        imgUrl = this.imgUrl,
        url = this.url,
        isOnlyImg = this.isOnlyImg,
    )
}

fun List<ProductDb>.asDomainModel(): List<Product> {
    return map { it.asDomainModel() }
}

fun ProductDb.asDomainModel(): Product {
    return Product(
        id = this.productId ?: 0,
        shopId = this.shopId ?: 0,
        shopName = this.shopName,
        name = this.name,
        subtitle = this.subtitle,
        price = this.price,
        amount = this.amount,
        details = this.details,
        promoInfo = this.promoInfo,
        promo = this.promo,
        imgUrl = this.imgUrl,
        url = this.url,
        isOnlyImg = this.isOnlyImg,
    )
}
