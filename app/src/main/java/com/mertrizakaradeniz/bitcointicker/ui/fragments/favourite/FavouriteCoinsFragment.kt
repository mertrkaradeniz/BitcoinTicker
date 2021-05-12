package com.mertrizakaradeniz.bitcointicker.ui.fragments.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertrizakaradeniz.bitcointicker.R
import com.mertrizakaradeniz.bitcointicker.adapters.CoinListAdapter
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.models.coin.CoinDetail
import com.mertrizakaradeniz.bitcointicker.databinding.FragmentFavouriteCoinsBinding
import com.mertrizakaradeniz.bitcointicker.ui.main.MainActivity
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteCoinsFragment : Fragment(R.layout.fragment_favourite_coins) {

    private var _binding: FragmentFavouriteCoinsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouriteCoinViewModel by viewModels()

    private lateinit var favouriteCoinListData: List<Coin>
    private lateinit var favouriteCoinListAdapter: CoinListAdapter

    private val _favouriteCoinList: MutableLiveData<Resource<CoinDetail>> = MutableLiveData()
    val favouriteCoinList: LiveData<Resource<CoinDetail>> = _favouriteCoinList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteCoinsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
        viewModel.getFavoriteCoins()

        favouriteCoinListAdapter.setOnItemClickListener { coin ->
            val bundle = Bundle().apply {
                putSerializable("coin", coin)
                putBoolean("isFavourite", true)
            }
            findNavController().navigate(R.id.action_favouriteCoinsFragment_to_coinDetailFragment,bundle)
        }
    }

    private fun setupObservers() {
        viewModel.favouriteCoins.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    favouriteCoinListData = response.data!!
                    favouriteCoinListAdapter.differ.submitList(favouriteCoinListData)
                    /* insert favourite coins into database*/
                }
                is Resource.Error -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setPositiveButton("Try Again") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.getFavoriteCoins()
                        }.setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
                is Resource.Loading -> {
                    (requireActivity() as MainActivity).showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        favouriteCoinListAdapter = CoinListAdapter()
        binding.rvFavouriteCoins.apply {
            adapter = favouriteCoinListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}