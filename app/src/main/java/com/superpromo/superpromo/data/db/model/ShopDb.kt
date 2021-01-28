package com.superpromo.superpromo.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shops")
data class ShopDb(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "categoryId")
    val categoryId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "imgUrl")
    val imgUrl: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "productCount")
    val productCount: Int?,

    @ColumnInfo(name = "isAvailable")
    val isAvailable: Boolean?,

    @ColumnInfo(name = "isAvailableInDb")
    val isAvailableInDb: Boolean,
)