package com.mertrizakaradeniz.bitcointicker.ui.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin
import com.mertrizakaradeniz.bitcointicker.databinding.FragmentCoinDetailBinding
import com.mertrizakaradeniz.bitcointicker.ui.fragments.list.CoinListViewModel

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!

    private val TAG = "CoinDetailFragment"
    private val viewModel: CoinDetailViewModel by viewModels<CoinDetailViewModel>()
    private val args: CoinDetailFragmentArgs by navArgs()

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
        /*coin= args.coin
        viewModel.fetchCoinDetail(requireContext(), coin.id)*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}