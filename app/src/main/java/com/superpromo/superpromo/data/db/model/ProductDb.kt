package com.superpromo.superpromo.data.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "products")
@Parcelize
data class ProductDb(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0L,

    @ColumnInfo(name = "shopId")
    val shopId: Int,

    @ColumnInfo(name = "shopName")
    var shopName: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "subtitle")
    val subtitle: String?,

    @ColumnInfo(name = "price")
    val price: Double?,

    @ColumnInfo(name = "amount")
    val amount: String?,

    @ColumnInfo(name = "details")
    val details: String?,

    @ColumnInfo(name = "promoInfo")
    val promoInfo: String?,

    @ColumnInfo(name = "promo")
    val promo: String?,

    @ColumnInfo(name = "imgUrl")
    val imgUrl: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "isOnlyImg")
    val isOnlyImg: Boolean?,
) : Parcelable