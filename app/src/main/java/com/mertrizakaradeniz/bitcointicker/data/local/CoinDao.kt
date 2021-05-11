package com.mertrizakaradeniz.bitcointicker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.models.coin.FavouriteCoin

@Dao
interface CoinDao {

    // COIN
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinList(coinList: List<Coin>)

    @Query("SELECT * FROM Coin")
    suspend fun getCoinList(): List<Coin>

    @Query("SELECT * FROM Coin WHERE name LIKE :query OR symbol LIKE :query")
    suspend fun getCoinList(query: String?): List<Coin>

    @Query("DELETE FROM Coin")
    suspend fun deleteCoinList()

    // FAVOURITE COIN
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCoin(favouriteCoin: FavouriteCoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCoins(list: List<FavouriteCoin>)

    @Query("SELECT * FROM FavouriteCoin WHERE id=:coinId AND userId=:userId")
    suspend fun getFavouriteCoin(coinId: String, userId: String): FavouriteCoin?

    @Query("SELECT * FROM FavouriteCoin WHERE userId=:userId")
    suspend fun getFavouriteCoins(userId: String): List<FavouriteCoin>

    @Query("DELETE FROM FavouriteCoin WHERE id=:coinId AND userId=:userId")
    suspend fun deleteFavouriteCoin(coinId: String, userId: String)

    @Query("SELECT * FROM FavouriteCoin WHERE userId=:userId")
    fun getCoinListLiveData(userId: String): LiveData<List<FavouriteCoin>>

}