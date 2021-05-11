package com.mertrizakaradeniz.bitcointicker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.models.coin.FavouriteCoin

@Database(entities = [Coin::class, FavouriteCoin::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}