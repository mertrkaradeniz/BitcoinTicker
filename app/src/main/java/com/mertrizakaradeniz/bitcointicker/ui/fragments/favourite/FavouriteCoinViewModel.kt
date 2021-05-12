package com.mertrizakaradeniz.bitcointicker.ui.fragments.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.repository.CoinRepository
import com.mertrizakaradeniz.bitcointicker.data.repository.FirestoreRepository
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteCoinViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _favouriteCoins = MutableLiveData<Resource<List<Coin>>>()
    val favouriteCoins: LiveData<Resource<List<Coin>>> = _favouriteCoins

    fun getFavoriteCoins() = viewModelScope.launch {
        safeGetFavoriteCoinsCall()
    }

    private suspend fun safeGetFavoriteCoinsCall() {
        _favouriteCoins.postValue(Resource.Loading())
        try {
            val collectionReference = firestoreRepository.favoriteCoinsCollection()
            collectionReference.get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        _favouriteCoins.postValue(Resource.Error("Favorite coins are empty"))
                    } else {
                        val coinList = ArrayList<Coin>()
                        for (snapShot in result) {
                            val id = snapShot.data["id"].toString()
                            val name = snapShot.data["name"].toString()
                            val symbol = snapShot.data["symbol"].toString()

                            val coinResponse = Coin(id, symbol, name)
                            coinList.add(coinResponse)
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            coinRepository.insertFavouriteCoins(coinList.toList())
                        }
                        _favouriteCoins.postValue(Resource.Success(coinList.toList()))
                    }
                }
                .addOnFailureListener {
                    _favouriteCoins.postValue(Resource.Error("An error occurred: ${it.message}"))
                }
        } catch (t: Throwable) {
            _favouriteCoins.postValue(Resource.Error("An error occurred: ${t.message}"))
        }
    }
}