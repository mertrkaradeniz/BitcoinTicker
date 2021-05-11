package com.mertrizakaradeniz.bitcointicker.ui.fragments.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.SetOptions
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.models.coin.CoinDetail
import com.mertrizakaradeniz.bitcointicker.data.repository.CoinRepository
import com.mertrizakaradeniz.bitcointicker.data.repository.FirebaseAuthRepository
import com.mertrizakaradeniz.bitcointicker.data.repository.FirestoreRepository
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import com.mertrizakaradeniz.bitcointicker.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _coinDetail: MutableLiveData<Resource<CoinDetail>> = MutableLiveData()
    val coinDetail: LiveData<Resource<CoinDetail>> = _coinDetail

    private val _addFavouriteCoin: MutableLiveData<Resource<String>> = MutableLiveData()
    val addFavouriteCoin: LiveData<Resource<String>> = _addFavouriteCoin

    private val _deleteFavouriteCoin: MutableLiveData<Resource<String>> = MutableLiveData()
    val deleteFavouriteCoin: LiveData<Resource<String>> = _deleteFavouriteCoin

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean> = _isFavourite

    fun fetchCoinDetail(context: Context, id: String) = viewModelScope.launch {
        safeFetchCoinDetailCall(context, id)
    }

    fun saveFavouriteCoin(coin: HashMap<String, String>) = viewModelScope.launch {
        safeSaveFavouriteCoinCall(coin)
    }

    fun deleteFavouriteCoin(coin: HashMap<String, String>) = viewModelScope.launch {
        safeDeleteFavouriteCoinCall(coin)
    }

    private suspend fun safeDeleteFavouriteCoinCall(coin: HashMap<String, String>) {
        _deleteFavouriteCoin.postValue(Resource.Loading())
        try {
            val collectionReference = firestoreRepository.favoriteCoinsCollection()
            collectionReference.document(coin["id"]!!)
                .delete()
                .addOnSuccessListener {
                    _deleteFavouriteCoin.postValue(Resource.Success("Coin deleted from favorite"))
                }
                .addOnFailureListener {
                    _deleteFavouriteCoin.postValue(Resource.Error("An error occurred: ${it.message}"))
                }
        } catch (t: Throwable) {
            _deleteFavouriteCoin.postValue(Resource.Error("An error occurred: ${t.message}"))
        }
    }

    private suspend fun safeSaveFavouriteCoinCall(coin: HashMap<String, String>) {
        _addFavouriteCoin.postValue(Resource.Loading())
        try {
            val collectionReference = firestoreRepository.favoriteCoinsCollection()
            collectionReference.document(coin["id"]!!)
                .set(coin)
                .addOnSuccessListener {
                    _addFavouriteCoin.postValue(Resource.Success("Coin added to favorite"))
                }
                .addOnFailureListener {
                    _addFavouriteCoin.postValue(Resource.Error("An error occurred: ${it.message}"))
                }
        } catch (t: Throwable) {
            _addFavouriteCoin.postValue(Resource.Error("An error occurred: ${t.message}"))
        }
    }


    private suspend fun safeFetchCoinDetailCall(context: Context, id: String) {
        _coinDetail.postValue(Resource.Loading())
        try {
            if (Utils.hasInternetConnection(context)) {
                val response = coinRepository.fetchCoinDetail(id)
                _coinDetail.postValue(handleCoinDetailResponse(response))
            } else {
                _coinDetail.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->
                    _coinDetail.postValue(Resource.Error("Network error"))
                else ->
                    _coinDetail.postValue(
                        Resource.Error("An error occurred, please try again")
                    )
            }
        }
    }

    private fun handleCoinDetailResponse(response: Response<CoinDetail>): Resource<CoinDetail> {
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                return Resource.Success(it)
            }
        } else {
            return Resource.Error(response.message())
        }
        return Resource.Error("An error occurred, please try again")
    }

    private fun checkIsFavourite(coinId: String, userId: String) = viewModelScope.launch {
        coinRepository.getFavouriteCoin(coinId, userId).also { favouriteCoin ->
            _isFavourite.postValue(favouriteCoin != null)
        }
    }
}