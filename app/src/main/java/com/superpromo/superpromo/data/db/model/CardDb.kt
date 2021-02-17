package com.superpromo.superpromo.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardDb(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "color")
    val color: String,

    @ColumnInfo(name = "number")
    val number: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}