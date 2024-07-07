package com.example.diffutil.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutil.databinding.ItemMonsterBinding
import com.example.diffutil.model.Monster
import com.google.android.material.snackbar.Snackbar
import java.util.Collections

class MonsterRvAdapter: ListAdapter<Monster, RecyclerView.ViewHolder>(homeDiffUtil) {
    companion object {
        private val homeDiffUtil = object : DiffUtil.ItemCallback<Monster>() {
            override fun areItemsTheSame(oldItem: Monster, newItem: Monster): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: Monster, newItem: Monster): Boolean =
                oldItem == newItem
        }
    }

    inner class MonsterViewHolder(private val binding: ItemMonsterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Monster){
            binding.tvName.text = "Name: ${data.name}"
            binding.tvLevel.text = "Level: ${data.level}"
            binding.tvStats.text = "HP: ${data.stats[0]} / MP: ${data.stats[1]} / Exp: ${data.stats[2]}"

            binding.vhLayout.setOnClickListener {
                Snackbar.make(it, "Item $adapterPosition touched!", Snackbar.LENGTH_SHORT).show()
            }
        }

        fun setAlpha(alpha: Float) {
            itemView.alpha = alpha
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MonsterViewHolder(ItemMonsterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return (holder as MonsterViewHolder).bind(getItem(position))
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val newList = currentList.toMutableList()
        Collections.swap(newList, fromPosition, toPosition)
        submitList(newList)
    }

    fun removeItem(position: Int) {
        val newList = currentList.toMutableList()
        newList.removeAt(position)
        submitList(newList)
    }
}