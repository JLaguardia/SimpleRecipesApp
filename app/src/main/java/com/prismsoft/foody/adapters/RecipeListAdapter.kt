//package com.prismsoft.foody.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.prismsoft.foody.data.model.RecipeResponse
//import com.prismsoft.foody.databinding.RecipesRowBinding
//import com.prismsoft.foody.data.model.Result
//
//class RecipeListAdapter() : ListAdapter<Result, RecipeListAdapter.RecipeViewHolder>(ResultCallback()) {
//    class RecipeViewHolder(private val binding: RecipesRowBinding) : ViewHolder(binding.root) {
//        fun bind(item: Result) {
//            binding.result = item
//            binding.executePendingBindings()
//        }
//
//        companion object {
//            fun from(parent: ViewGroup): RecipeViewHolder {
//                val inflater = LayoutInflater.from(parent.context)
//                return RecipeViewHolder(
//                    RecipesRowBinding.inflate(inflater)
//                )
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
//        RecipeViewHolder.from(parent)
//
//
//    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
//        holder.bind(getItem(position))
//
//    fun setData(response: RecipeResponse) = submitList(response.results)
//}
//
//class ResultCallback: DiffUtil.ItemCallback<Result>() {
//    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
//        oldItem.sourceName == newItem.sourceName
//
//    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem == newItem
//}