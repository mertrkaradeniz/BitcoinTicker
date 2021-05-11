package com.mertrizakaradeniz.bitcointicker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mertrizakaradeniz.bitcointicker.databinding.CoinItemBinding
import com.mertrizakaradeniz.bitcointicker.data.models.coin.Coin

class CoinListAdapter : RecyclerView.Adapter<CoinListAdapter.CoinsViewHolder>() {

    inner class CoinsViewHolder(val binding: CoinItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder(
            CoinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.binding.apply {
            tvCoinName.text = currentItem.name
            tvCoinSymbol.text = currentItem.symbol

            root.setOnClickListener {
                onItemClickListener?.let { it(currentItem) }
            }
        }

    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Coin) -> Unit)? = null

    fun setOnItemClickListener(listener: (Coin) -> Unit) {
        onItemClickListener = listener
    }

}