package com.mertrizakaradeniz.bitcointicker.ui.fragments.detail

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.mertrizakaradeniz.bitcointicker.R
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.data.models.coin.CoinDetail
import com.mertrizakaradeniz.bitcointicker.databinding.FragmentCoinDetailBinding
import com.mertrizakaradeniz.bitcointicker.ui.main.MainActivity
import com.mertrizakaradeniz.bitcointicker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail) {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CoinDetailViewModel by viewModels<CoinDetailViewModel>()
    //private val args: CoinDetailFragmentArgs by navArgs()

    private lateinit var coin: Coin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coin = arguments?.get("coin") as Coin
        setupObservers()
        //setupIntervalChange()
        //setupAddFavorite()

        viewModel.fetchCoinDetail(requireContext(), coin.id)
    }

    private fun setupObservers() {

        viewModel.coinDetail.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    if (response.data != null) {
                        setCoinDetail(response.data)
                        binding.clRoot.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setPositiveButton("Try Again") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.fetchCoinDetail(requireContext(), coin.id)
                        }.show()
                }
                is Resource.Loading -> {
                    (requireActivity() as MainActivity).showProgressBar()
                    binding.clRoot.visibility = View.GONE
                }
            }
        }


    }

    private fun setCoinDetail(coinDetailResponse: CoinDetail) {
        binding.apply {
            imgCoinImage.load(coinDetailResponse.image.small) {
                crossfade(true)
            }
            tvCoinName.text = coinDetailResponse.name
            tvCoinSymbol.text = coinDetailResponse.symbol
            tvCoinLastRefresh.text = coinDetailResponse.marketData.lastUpdated.toString()

            if (coinDetailResponse.marketData.currentPrice.usd.toString() != "") {
                tvCoinCurrentPrice.text = coinDetailResponse.marketData.currentPrice.usd.toString()
            } else {
                tvCoinCurrentPrice.text = "No data"
            }
            if (coinDetailResponse.marketData.priceChangePercentage24h.toString() != "") {
                tvCoinPriceChangePercentage.text =
                    coinDetailResponse.marketData.priceChangePercentage24h.toString()
            } else {
                tvCoinPriceChangePercentage.text = "No data"
            }
            if (coinDetailResponse.hashingAlgorithm != "") {
                tvcoinHashingAlgorithm.text = coinDetailResponse.hashingAlgorithm
            } else {
                tvcoinHashingAlgorithm.text = "No Data"
            }
            if (coinDetailResponse.description.en != "") {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvcoinDescription.text = Html.fromHtml(
                        coinDetailResponse.description.en,
                        Html.FROM_HTML_MODE_COMPACT
                    )
                } else {
                    tvcoinDescription.text = Html.fromHtml(coinDetailResponse.description.en)
                }
            } else {
                tvcoinDescription.text = "No Data"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}