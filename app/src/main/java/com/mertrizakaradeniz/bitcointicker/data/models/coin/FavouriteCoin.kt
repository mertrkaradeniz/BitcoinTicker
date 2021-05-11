package com.mertrizakaradeniz.bitcointicker.data.models.coin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteCoin")
data class FavouriteCoin(
    @PrimaryKey()
    val id: String,
    val userId: String,
    val name: String,
    val symbol: String
)