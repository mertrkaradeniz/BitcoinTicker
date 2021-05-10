package com.mertrizakaradeniz.bitcointicker.model.coin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteCoin")
data class FavouriteCoin(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val userId: String,
    val name: String,
    val symbol: String
)