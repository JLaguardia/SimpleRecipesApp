package com.prismsoft.foody.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.prismsoft.foody.R

class RecipesRowBindingAdapter {

    companion object {

        @BindingAdapter("setIntText")
        @JvmStatic
        fun setIntText(textView: TextView, likes: Int) {
            textView.text = "$likes"
        }

        @BindingAdapter("setVegan")
        @JvmStatic
        fun setVegan(view: View, isVegan: Boolean) {
            val context = view.context
            val colorRes = if (isVegan) {
                R.color.green
            } else {
                R.color.black
            }
            when(view) {
                is TextView -> view.setTextColor(context.getColor(colorRes))
                is ImageView -> view.imageTintList = ColorStateList.valueOf(context.getColor(colorRes))
                else -> Unit
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
            }
        }
    }
}