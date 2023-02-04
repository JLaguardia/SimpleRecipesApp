package com.prismsoft.foody.ui.views.recipes

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.prismsoft.foody.MainViewModel
import com.prismsoft.foody.R
import com.prismsoft.foody.data.NetworkResult
import com.prismsoft.foody.data.model.RecipeResponse
import com.prismsoft.foody.data.model.Result
import com.prismsoft.foody.ui.theme.FoodyTheme
import com.prismsoft.foody.ui.views.navigation.NavigatorManager
import com.prismsoft.foody.ui.views.recipes.custom.Leaf
import com.prismsoft.foody.ui.views.shimmer.ShimmerListItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class RecipesView(
    val viewModel: MainViewModel,
    val navigatorManager: NavigatorManager,
    context: Context
) {
    init {
//        viewModel.getRecipes(context = context)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun RecipesList() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        var retry by remember { mutableStateOf(false) }
        var currentDistance by remember { mutableStateOf(0f) }
        val threshold = with(LocalDensity.current) { 160.dp.toPx() }

        var refreshing by remember { mutableStateOf(false) }

        var result by remember {
            mutableStateOf<NetworkResult<RecipeResponse>>(NetworkResult.Loading())
        }
        val progress = currentDistance / threshold

        fun doRefresh() {
            scope.launch {
                Log.i("JAMES::", "refreshing... $refreshing")
                refreshing = true
                viewModel.getRecipes(context = context)
            }
        }

        fun onPull(pullDelta: Float): Float = when {
            refreshing -> 0f
            else -> {
                val newOffset = (currentDistance + pullDelta).coerceAtLeast(0f)
                val dragConsumed = newOffset - currentDistance
                currentDistance = newOffset
                dragConsumed
            }
        }

        suspend fun onRelease() {
            if (refreshing) return // Already refreshing - don't call refresh again.
            if (currentDistance > threshold) doRefresh()

            animate(initialValue = currentDistance, targetValue = 0f) { value, _ ->
                currentDistance = value
            }
        }

        LaunchedEffect(key1 = retry) {
            viewModel.recipesResponse.collectLatest {
                Log.i("JAMES::", "result: ${it::class.simpleName}")
                refreshing = false
                result = it
            }
        }

        Box(Modifier
            .pullRefresh(
                onPull = ::onPull,
                onRelease = { onRelease() }
            )
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                when {
                    refreshing || result is NetworkResult.Loading -> {
                        items(7) {
                            ShimmerListItem(isLoading = true) { }
                        }
                    }
                    result is NetworkResult.Success -> {
                        items(result.data?.results ?: emptyList()) { item ->
                            RecipeListItem(item = item.toRecipeItemData())
                        }
                    }
                    else -> {
                        item {
                            Column(modifier = Modifier
                                .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    modifier = Modifier.size(100.dp),
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "Error"
                                )
                            }
                        }
                    }
                }
            }

            // Custom progress indicator
            HorizontalProgressBar(progress = progress, refreshing = refreshing)
        }
    }
}

@Composable
fun HorizontalProgressBar(progress: Float, refreshing: Boolean){
    AnimatedVisibility(visible = (refreshing || progress > 0)) {
        if (refreshing) {
            LinearProgressIndicator(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )
        } else {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

private fun Result.toRecipeItemData() =
    RecipeItemData(
        title = this.title,
        body = this.summary,
        likes = this.aggregateLikes,
        time = this.readyInMinutes.toFloat(),
        isVegan = this.vegan,
        imageUrl = this.image
    )

@Composable
fun RecipeListItem(
    item: RecipeItemData
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(0.45f),
                model = item.imageUrl,
                contentDescription = item.title,
                placeholder = painterResource(id = R.drawable.ic_restaurant),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(
                    text = item.title,
                    overflow = TextOverflow.Ellipsis,
//                    fontFamily = FontFamily(Font(R.font.courgette)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    text = item.body
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            tint = colorResource(id = R.color.red),
                            contentDescription = "favorited"
                        )
                        Text(
                            text = item.likes.toString(),
                            color = colorResource(id = R.color.red)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clock),
                            tint = colorResource(id = R.color.yellow),
                            contentDescription = "time"
                        )
                        Text(
                            text = item.time.toString(),
                            color = colorResource(id = R.color.yellow)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val color = colorResource(
                            id = if (item.isVegan) {
                                R.color.green
                            } else {
                                R.color.black
                            }
                        )
                        Icon(
                            imageVector = CustomIcons.Leaf,
                            tint = color,
                            contentDescription = "Vegan"
                        )
                        Text(
                            text = "Vegan",
                            color = color
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun RecipePreview() {
    var item by remember {
        mutableStateOf(testItems[0])
    }
    FoodyTheme {
        RecipeListItem(item)
    }

    LaunchedEffect(key1 = "test") {
        var cycles = 0
        while (cycles < 10) {
            delay(5000L)
            item = item.copy(
                title = if (item.isVegan) {
                    "non vegan"
                } else {
                    "Test Vegan"
                },
                isVegan = !item.isVegan,
                likes = item.likes + 24
            )
            cycles++
        }
    }
}

val testItems = listOf(
    RecipeItemData(
        title = "Test",
        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco",
        likes = 1340,
        time = 15.5f,
        isVegan = false,
        imageUrl = null
    )
)

data class RecipeItemData(
    val title: String,
    val body: String,
    val likes: Int,
    val time: Float,
    val isVegan: Boolean,
    val imageUrl: String?
)