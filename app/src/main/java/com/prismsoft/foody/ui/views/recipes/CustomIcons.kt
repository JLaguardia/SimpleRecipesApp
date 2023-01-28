package com.prismsoft.foody.ui.views.recipes

import androidx.compose.ui.graphics.vector.ImageVector
import com.prismsoft.foody.ui.views.recipes.custom.Leaf

object CustomIcons

private var allIcons: List<ImageVector>? = null

val CustomIcons.AllIcons: List<ImageVector>
  get() {
    if (allIcons != null) {
      return allIcons!!
    }
    allIcons= listOf(Leaf)
    return allIcons!!
  }
