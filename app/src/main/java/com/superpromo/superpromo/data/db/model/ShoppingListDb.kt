package com.superpromo.superpromo.data.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "shopping_lists")
@Parcelize
data class ShoppingListDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "created")
    val created: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "productCount")
    val productCount: Int = 0,

    @ColumnInfo(name = "productCountActive")
    val productCountActive: Int = 0,

    @ColumnInfo(name = "isArchived")
    val isArchived: Boolean = false
) : Parcelable