package com.prismsoft.foody.ui.views.shimmer

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prismsoft.foody.R

import io.supercharge.shimmerlayout.ShimmerLayout

class ShimmerViewHolderCpy(inflater: LayoutInflater, parent: ViewGroup, innerViewResId: Int) : RecyclerView.ViewHolder(inflater.inflate(
    R.layout.shimmer_item_view, parent, false)) {

    private val mShimmerLayout: ShimmerLayout = itemView as ShimmerLayout

    init {
        inflater.inflate(innerViewResId, mShimmerLayout, true)
    }

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