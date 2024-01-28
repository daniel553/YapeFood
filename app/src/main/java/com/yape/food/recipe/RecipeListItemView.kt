package com.yape.food.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yape.food.R
import com.yape.food.model.RecipeItem
import com.yape.food.ui.theme.YapeFoodTheme

val itemViewHeight = 280.dp

@Preview(showBackground = true)
@Composable
fun RecipeListItemViewPreview() {
    YapeFoodTheme {
        RecipeListItemView(
            RecipeItem(0, "Recipe name"),
            onClick = {},
            modifier = Modifier.height(itemViewHeight)
        )
    }
}


//💡To be reused by the lazy column items
@Composable
fun RecipeListItemView(
    recipe: RecipeItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .shadow(elevation = 2.dp, clip = false, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }) {
        Box {
            if (recipe.url.isNullOrEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.lunch),
                    contentScale = ContentScale.Crop,
                    contentDescription = "food",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                AsyncImage(
                    model = recipe.url,
                    contentDescription = "recipe",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.2f to Color.Transparent,
                                0.99f to Color.DarkGray
                            )
                        )
                    )
            ) {
                Text(
                    text = recipe.name.uppercase(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .widthIn(max = 180.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "rating",
                        tint = Color.White,
                        modifier = Modifier.height(22.dp)
                    )
                    Text(
                        text = recipe.rating.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*Nothing to do*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "like",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
