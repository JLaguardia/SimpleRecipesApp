package com.prismsoft.foody.ui.views.shimmer

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.prismsoft.foody.R
import com.prismsoft.foody.ui.theme.FoodyTheme
import com.prismsoft.foody.ui.views.recipes.RecipeListItem
import com.prismsoft.foody.ui.views.recipes.testItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        ShimmerItem()
    } else {
        content()
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = InfiniteRepeatableSpec(tween(durationMillis = 1000))
    )

    background(
        Brush.linearGradient(
            colors = listOf(
                Color(0x36000000),
                Color(0x36FFFFFF),
                Color(0x36000000)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
fun ShimmerItem() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 32.dp, vertical = 18.dp)
    ) {
        val lessAlpha = MaterialTheme.colors.onBackground.copy(alpha = 0.2f)
        Box(
            modifier = Modifier
                .width(84.dp)
                .height(68.dp)
                .background(lessAlpha)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(lessAlpha)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(120.dp)
                    .height(10.dp)
                    .background(lessAlpha)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(lessAlpha)
                    .shimmerEffect()
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun ShimmerItemPreview() {
    val scope = rememberCoroutineScope()
    var loading by remember {
        mutableStateOf(true)
    }

    FoodyTheme {
        Column {
            for (i in 1..5) {
                ShimmerListItem(loading) { RecipeListItem(item = testItems[0]) }
            }
        }
    }

    LaunchedEffect(key1 = loading) {
        scope.launch {
            delay(5000L)
            loading = !loading
        }
    }
}

@Preview(
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun ShimmerItemPreviewNight() {
    val scope = rememberCoroutineScope()
    var loading by remember {
        mutableStateOf(true)
    }

    FoodyTheme {
        Column {
            for (i in 1..5) {
                ShimmerListItem(loading) {
                    RecipeListItem(item = testItems[0])
                }
            }
        }
    }

    LaunchedEffect(key1 = loading) {
        scope.launch {
            delay(5000L)
            loading = !loading
        }
    }
}