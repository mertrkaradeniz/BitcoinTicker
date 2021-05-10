package com.mertrizakaradeniz.bitcointicker.data.repository

import com.mertrizakaradeniz.bitcointicker.data.local.dao.CoinDao
import com.mertrizakaradeniz.bitcointicker.data.remote.CoinApi

class CoinRepository(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao
) {

}