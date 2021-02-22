package com.superpromo.superpromo.data.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "products")
@Parcelize
data class ProductDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "productId")
    val productId: Long? = null,

    @ColumnInfo(name = "shoppingListId")
    val shoppingListId: Long,

    @ColumnInfo(name = "shopName")
    var shopName: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "subtitle")
    val subtitle: String? = null,

    @ColumnInfo(name = "price")
    val price: Double? = null,

    @ColumnInfo(name = "amount")
    val amount: String? = null,

    @ColumnInfo(name = "details")
    val details: String? = null,

    @ColumnInfo(name = "promoInfo")
    val promoInfo: String? = null,

    @ColumnInfo(name = "promo")
    val promo: String? = null,

    @ColumnInfo(name = "imgUrl")
    val imgUrl: String? = null,

    @ColumnInfo(name = "url")
    val url: String? = null,

    @ColumnInfo(name = "isLocal")
    val isLocal: Boolean? = null,

    @ColumnInfo(name = "isOnlyImg")
    val isOnlyImg: Boolean? = null,

    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean = false,
) : Parcelable