package com.mertrizakaradeniz.bitcointicker.data.repository

import androidx.lifecycle.LiveData
import com.mertrizakaradeniz.bitcointicker.data.local.CoinDao
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.models.coin.CoinDetail
import com.mertrizakaradeniz.bitcointicker.data.models.coin.FavouriteCoin
import com.mertrizakaradeniz.bitcointicker.data.remote.CoinApi
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao
) {

    suspend fun fetchCoinList() = coinApi.fetchCoinList()

    suspend fun fetchCoinDetail(id: String) = coinApi.fetchCoinDetail(id)

    suspend fun insertCoinList(coinList: List<Coin>) = coinDao.insertCoinList(coinList)

    suspend fun getCoinList() = coinDao.getCoinList()

    suspend fun getCoinList(query: String?) = coinDao.getCoinList(query)

    suspend fun deleteCoinList() = coinDao.deleteCoinList()

    suspend fun insertFavouriteCoin(favouriteCoin: FavouriteCoin) =
        coinDao.insertFavouriteCoin(favouriteCoin)

    suspend fun insertFavouriteCoins(list: List<Coin>) = coinDao.insertFavouriteCoins(list)

    suspend fun getFavouriteCoin(coinId: String, userId: String) = coinDao.getFavouriteCoin(coinId, userId)

    suspend fun getFavouriteCoinList(userId: String) = coinDao.getFavouriteCoins(userId)

    suspend fun deleteFavouriteCoin(coinId: String, userId: String) = coinDao.deleteFavouriteCoin(coinId, userId)

    fun getCoinListLiveData(userId: String) = coinDao.getCoinListLiveData(userId)

/*
    fun getCoinList(): LiveData<List<FavouriteCoin>> {
        return coinDao.getCoinListLiveData(firebaseAuthRepository.getCurrentUser()!!)
    }

    suspend fun getCoinList(onResult: (Resource<List<Coin>>) -> Unit) {
        coinDao.getCoinList().also {
            onResult(Resource.Loading())
        }
        coinApi.fetchCoinList().also { response ->
            GlobalScope.launch(Dispatchers.IO) {
                if (response.isSuccessful) {
                    (response.body() ?: listOf()).also { coinList ->
                        coinDao.deleteCoinList()
                        coinDao.insertCoinList(coinList)
                        onResult(Resource.Success(coinList))
                    }
                } else {
                    onResult(
                        Resource.Error(
                            response.errorBody()?.string() ?: "An unexpected error occurred."
                        )
                    )
                }
            }
        }
    }

    suspend fun getCoinListByQuery(query: String?, onResult: (Resource<List<Coin>>) -> Unit) {
        onResult(Resource.Loading())
        if (query == null || query.isBlank()) {
            onResult(Resource.Success(coinDao.getCoinList()))
        } else {
            val searchQuery = "%$query%"
            onResult(Resource.Success(coinDao.getCoinList(searchQuery)))
        }
    }

    suspend fun getCoinDetails(id: String, onResult: (Resource<CoinDetail?>) -> Unit) {
        onResult(Resource.Loading())
        coinApi.fetchCoinDetail(id).also { response ->
            if (response.isSuccessful) {
                onResult(Resource.Success(response.body()))
            } else {
                onResult(Resource.Error(response.message()))
            }
        }
    }

    suspend fun getFavouriteCoin(coinId: String): FavouriteCoin? {
        return withContext(Dispatchers.IO) {
            coinDao.getFavouriteCoin(
                coinId,
                firebaseAuthRepository.getCurrentUser()!!
            )
        }
    }

    suspend fun deleteFavouriteCoin(coinId: String) {
        coinDao.deleteFavouriteCoin(coinId, firebaseAuthRepository.getCurrentUser()!!)
    }

    suspend fun addToFavourites(coin: FavouriteCoin) {
        coinDao.insertFavouriteCoin(coin)
    }

    suspend fun getFavouriteCoins(): List<FavouriteCoin> {
        return withContext(Dispatchers.IO) { coinDao.getFavouriteCoins(firebaseAuthRepository.getCurrentUser()!!) }
    }

    suspend fun insertFavouriteCoins(list: List<FavouriteCoin>) {
        coinDao.insertFavouriteCoins(list)
    }
*/
}