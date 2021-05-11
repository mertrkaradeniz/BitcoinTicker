package com.mertrizakaradeniz.bitcointicker.ui.fragments.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.mertrizakaradeniz.bitcointicker.data.models.coin.CoinDetail
import com.mertrizakaradeniz.bitcointicker.data.repository.CoinRepository
import com.mertrizakaradeniz.bitcointicker.data.repository.FirebaseAuthRepository
import com.mertrizakaradeniz.bitcointicker.data.repository.FirestoreRepository
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import com.mertrizakaradeniz.bitcointicker.utils.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel() {

    private val _coinDetailList: MutableLiveData<Resource<CoinDetail>> = MutableLiveData()
    val coinDetailList: LiveData<Resource<CoinDetail>> = _coinDetailList

    private val _favouriteCoinList: MutableLiveData<Resource<CoinDetail>> = MutableLiveData()
    val favouriteCoinList: LiveData<Resource<CoinDetail>> = _coinDetailList

    fun fetchCoinDetail(context: Context, id: String) = viewModelScope.launch {
        safeFetchCoinDetailCall(context, id)
    }

    fun saveFavoriteCoin(coin: HashMap<String, String>, onResult: (Resource<Task<Void>>) -> Unit) = viewModelScope.launch {
        onResult(Resource.Loading())
        try {
            val collectionReference = firestoreRepository.favoriteCoinsCollection()
            collectionReference.document(coin["id"]!!)
                .set(coin)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(Resource.Success(task))
                    } else {
                        onResult(Resource.Error(task.exception?.localizedMessage ?: "Unexpected error while adding to favourites."))
                    }
                }
        } catch (t: Throwable) {
            onResult(Resource.Error("An error occurred: ${t.message}"))
        }
    }

    private suspend fun safeFetchCoinDetailCall(context: Context, id: String) {
        _coinDetailList.postValue(Resource.Loading())
        try {
            if (Utils.hasInternetConnection(context)) {
                val response = coinRepository.fetchCoinDetail(id)
                _coinDetailList.postValue(handleCoinDetailResponse(response))
            } else {
                _coinDetailList.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->
                    _coinDetailList.postValue(Resource.Error("Network error"))
                else ->
                    _coinDetailList.postValue(
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


}