package com.prismsoft.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.prismsoft.foody.data.model.RecipeResponse
import com.prismsoft.foody.databinding.RecipesRowBinding
import com.prismsoft.foody.data.model.Result
import com.prismsoft.foody.util.RecipesDiffUtilCallback


class RecipeListAdapter() :
    ListAdapter<Result, RecipeListAdapter.RecipeViewHolder>(RecipesDiffUtilCallback()) {

    class RecipeViewHolder(private val binding: RecipesRowBinding) : ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.result = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                val binding =
                    RecipesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RecipeViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun setData(newData: RecipeResponse) = submitList(newData.results)

}