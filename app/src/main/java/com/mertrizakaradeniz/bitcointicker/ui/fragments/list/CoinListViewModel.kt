package com.mertrizakaradeniz.bitcointicker.ui.fragments.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertrizakaradeniz.bitcointicker.data.repository.CoinRepository
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import com.mertrizakaradeniz.bitcointicker.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _coinList: MutableLiveData<Resource<List<Coin>>> = MutableLiveData()
    val coinList: LiveData<Resource<List<Coin>>> = _coinList

    private val _filteredCoinList: MutableLiveData<Resource<List<Coin>>> = MutableLiveData()
    val filteredCoinList: LiveData<Resource<List<Coin>>> = _filteredCoinList

    fun fetchCoinList(context: Context) = viewModelScope.launch {
        safeFetchCoinListCall(context)
    }

    fun insertCoinList(coinList: List<Coin>) = viewModelScope.launch {
        coinRepository.insertCoinList(coinList)
    }

    fun filterCoinList(query: String) = viewModelScope.launch {
        filterCoinListCall(query)
    }

    private suspend fun filterCoinListCall(query: String) {
        _filteredCoinList.postValue(Resource.Loading())
        try {
            val result = coinRepository.getCoinList(query)
            if (result.isNotEmpty())
                _filteredCoinList.postValue(Resource.Success(result))
            else
                _filteredCoinList.postValue(Resource.Error("Coins not found"))
        } catch (t: Throwable) {
            _filteredCoinList.postValue(Resource.Error("An error occurred, please try again: ${t.message}"))
        }
    }

    private suspend fun safeFetchCoinListCall(context: Context) {
        _coinList.postValue(Resource.Loading())
        try {
            if (Utils.hasInternetConnection(context)) {
                val response = coinRepository.fetchCoinList()
                _coinList.postValue(handleCoinListResponse(response))
            } else {
                _coinList.postValue(Resource.Error("No internet connection", emptyList()))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _coinList.postValue(Resource.Error("Network Failure"))
                else -> _coinList.postValue(Resource.Error("An error occurred, please try again"))
            }
        }
    }

    private fun handleCoinListResponse(response: Response<List<Coin>>): Resource<List<Coin>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        } else {
            return Resource.Error(response.message())
        }
        return Resource.Error("An error occurred, please try again")
    }

}