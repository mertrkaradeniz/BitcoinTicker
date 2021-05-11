package com.mertrizakaradeniz.bitcointicker.data.remote

import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.models.coin.CoinDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApi {

    @GET("coins/list")
    suspend fun fetchCoinList(): Response<List<Coin>>

    @GET("coins/{id}")
    suspend fun fetchCoinDetail(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = true,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkLine: Boolean = false
    ): Response<CoinDetail>

    /*@GET("coins/{id}")
    suspend fun getCoinDetail(@Path("id") id: String): Response<CoinDetail>*/
}