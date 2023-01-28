package com.prismsoft.foody.ui.views.recipes.custom

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.prismsoft.foody.ui.views.recipes.CustomIcons

val CustomIcons.Leaf: ImageVector
    get() {
        if (_leaf != null) {
            return _leaf!!
        }
//        _leaf = Builder(name = "Leaf", defaultWidth = 1.0.dp, defaultHeight = 1.0.dp, viewportWidth
//                = 24.0f, viewportHeight = 24.0f).apply {
//            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
//                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
//                    pathFillType = NonZero) { this.leafIt() }
        _leaf = materialIcon("leaf") { materialPath { this.leafIt() } }
        return _leaf!!
    }

private fun PathBuilder.leafIt() {
    moveTo(17.0f, 8.0f)
    curveTo(8.0f, 10.0f, 5.9f, 16.17f, 3.82f, 21.34f)
    lineToRelative(1.89f, 0.66f)
    lineToRelative(0.95f, -2.3f)
    curveToRelative(0.48f, 0.17f, 0.98f, 0.3f, 1.34f, 0.3f)
    curveTo(19.0f, 20.0f, 22.0f, 3.0f, 22.0f, 3.0f)
    curveToRelative(-1.0f, 2.0f, -8.0f, 2.25f, -13.0f, 3.25f)
    reflectiveCurveTo(2.0f, 11.5f, 2.0f, 13.5f)
    reflectiveCurveToRelative(1.75f, 3.75f, 1.75f, 3.75f)
    curveTo(7.0f, 8.0f, 17.0f, 8.0f, 17.0f, 8.0f)
    close()
}

private var _leaf: ImageVector? = null
