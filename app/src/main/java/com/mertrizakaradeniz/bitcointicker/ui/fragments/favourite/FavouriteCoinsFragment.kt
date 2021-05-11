package com.mertrizakaradeniz.bitcointicker.ui.fragments.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mertrizakaradeniz.bitcointicker.data.models.coin.CoinDetail
import com.mertrizakaradeniz.bitcointicker.databinding.FragmentFavouriteCoinsBinding
import com.mertrizakaradeniz.bitcointicker.utils.Resource

class FavouriteCoinsFragment : Fragment() {

    private var _binding: FragmentFavouriteCoinsBinding? = null
    private val binding get() = _binding!!

    private val _favouriteCoinList: MutableLiveData<Resource<CoinDetail>> = MutableLiveData()
    val favouriteCoinList: LiveData<Resource<CoinDetail>> = _favouriteCoinList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteCoinsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}