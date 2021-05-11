package com.mertrizakaradeniz.bitcointicker.di

import com.google.firebase.auth.FirebaseAuth
import com.mertrizakaradeniz.bitcointicker.data.local.CoinDao
import com.mertrizakaradeniz.bitcointicker.data.remote.CoinApi
import com.mertrizakaradeniz.bitcointicker.data.repository.CoinRepository
import com.mertrizakaradeniz.bitcointicker.data.repository.FirebaseAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //REPOSITORY
    @Singleton
    @Provides
    fun provideCoinRepository(
        coinApi: CoinApi,
        coinDao: CoinDao,
        firebaseAuthRepository: FirebaseAuthRepository
    ): CoinRepository = CoinRepository(coinApi, coinDao, firebaseAuthRepository)

    @Singleton
    @Provides
    fun provideFirebaseAuthRepository(firebaseAuth: FirebaseAuth) =
        FirebaseAuthRepository(firebaseAuth)
}