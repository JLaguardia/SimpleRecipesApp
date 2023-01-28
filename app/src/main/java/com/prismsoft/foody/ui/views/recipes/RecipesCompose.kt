package com.prismsoft.foody.ui.views.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prismsoft.foody.R
import com.prismsoft.foody.data.model.Result
import com.prismsoft.foody.ui.theme.FoodyTheme
import com.prismsoft.foody.ui.views.recipes.custom.Leaf
import kotlinx.coroutines.delay

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
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                ,
                imageVector = Icons.Filled.Face,
                contentScale = ContentScale.Crop,
                contentDescription = "")
            Column {
                Text(
                    text = item.title,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.courgette)),
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
                            contentDescription = "favorited")
                        Text(
                            text = item.likes.toString(),
                            color = colorResource(id = R.color.red)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clock),
                            tint = colorResource(id = R.color.yellow),
                            contentDescription = "time")
                        Text(
                            text = item.time.toString(),
                            color = colorResource(id = R.color.yellow)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val color = colorResource(id = if(item.isVegan){
                             R.color.green } else { R.color.black } )
                        Icon(
                            imageVector = CustomIcons.Leaf,
                            tint = color,
                            contentDescription = "Vegan")
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

@Preview(showSystemUi = false)
@Composable
fun RecipePreview() {
//    val result = Result(
//        1,
//        true,
//    )
    var item by remember {
       mutableStateOf(testItems[0])
    }
    FoodyTheme {
        RecipeListItem(item)
    }

    LaunchedEffect(key1 = "test"){
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