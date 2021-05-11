package com.mertrizakaradeniz.bitcointicker.ui.fragments.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertrizakaradeniz.bitcointicker.R
import com.mertrizakaradeniz.bitcointicker.adapters.CoinListAdapter
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.databinding.FragmentCoinListBinding
import com.mertrizakaradeniz.bitcointicker.ui.main.MainActivity
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListFragment : Fragment(R.layout.fragment_coin_list) {

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!
    private val TAG = "CoinListFragment"

    private val viewModel: CoinListViewModel by viewModels<CoinListViewModel>()

    private lateinit var coinListData: List<Coin>
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()

        viewModel.getCoinList(requireContext())
    }

    private fun setupObservers() {

        viewModel.coinList.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    coinListData = response.data!!
                    coinListAdapter.differ.submitList(coinListData)
                    /*
                    recyclerView.scheduleLayoutAnimation()
                    mainViewModel.insertCoinList(coinListData)
                    */
                }
                is Resource.Error -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setCancelable(false)
                        .setPositiveButton("Try Again") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.getCoinList(requireContext())
                        }.show()
                    /*
                    response.message?.let { message ->
                        Toast.makeText(activity, "An Error occurred: $message", Toast.LENGTH_LONG).show()
                    }
                    */
                }
                is Resource.Loading -> {
                    (requireActivity() as MainActivity).showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        coinListAdapter = CoinListAdapter()
        binding.rvCoins.apply {
            adapter = coinListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}