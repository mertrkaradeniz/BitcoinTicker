package com.mertrizakaradeniz.bitcointicker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mertrizakaradeniz.bitcointicker.data.local.dao.CoinDao
import com.mertrizakaradeniz.bitcointicker.model.coin.Coin
import com.mertrizakaradeniz.bitcointicker.model.coin.FavouriteCoin

@Database(entities = [Coin::class, FavouriteCoin::class], version = 1)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}