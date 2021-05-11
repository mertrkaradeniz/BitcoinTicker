package com.mertrizakaradeniz.bitcointicker.data.repository

import com.mertrizakaradeniz.bitcointicker.data.local.CoinDao
import com.mertrizakaradeniz.bitcointicker.data.remote.CoinApi
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao,
    private val firebaseAuthRepository: FirebaseAuthRepository
) {

    suspend fun getCoinList() = coinApi.fetchCoinList()

    /*fun getCoinItemList(): LiveData<List<FavouriteCoin>> {
        return coinDao.getCoinItemListLiveData(firebaseAuthRepository.getCurrentUser()!!)
    }*/

}