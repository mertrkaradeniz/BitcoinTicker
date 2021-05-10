package com.mertrizakaradeniz.bitcointicker.model.coin

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Coin")
data class Coin(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name: String
)