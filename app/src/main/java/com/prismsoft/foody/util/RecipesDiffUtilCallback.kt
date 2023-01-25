package com.prismsoft.foody.util

import androidx.recyclerview.widget.DiffUtil
import com.prismsoft.foody.data.model.Result

class RecipesDiffUtilCallback : DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
        oldItem == newItem

}