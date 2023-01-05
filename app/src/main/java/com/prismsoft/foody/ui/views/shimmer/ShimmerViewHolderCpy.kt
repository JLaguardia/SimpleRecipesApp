package com.prismsoft.foody.ui.views.shimmer

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.prismsoft.foody.databinding.ShimmerItemViewBinding

import io.supercharge.shimmerlayout.ShimmerLayout

class ShimmerViewHolderCpy(private val binding: ShimmerItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val mShimmerLayout: ShimmerLayout = itemView as ShimmerLayout


    fun setShimmerAngle(angle: Int) {
        mShimmerLayout.setShimmerAngle(angle)
    }

    fun setShimmerColor(color: Int) {
        mShimmerLayout.setShimmerColor(color)
    }

    fun setShimmerMaskWidth(maskWidth: Float) {
        mShimmerLayout.setMaskWidth(maskWidth)
    }

    fun setShimmerViewHolderBackground(viewHolderBackground: Drawable?) {
        if (viewHolderBackground != null) {
            setBackground(viewHolderBackground)
        }
    }

    fun setShimmerAnimationDuration(duration: Int) {
        mShimmerLayout.setShimmerAnimationDuration(duration)
    }

    fun setAnimationReversed(animationReversed: Boolean) {
        mShimmerLayout.setAnimationReversed(animationReversed)
    }

    fun bind() {
        mShimmerLayout.startShimmerAnimation()
    }

    private fun setBackground(background: Drawable) {
        mShimmerLayout.background = background
    }
}