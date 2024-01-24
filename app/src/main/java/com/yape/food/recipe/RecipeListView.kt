package com.yape.food.recipe

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yape.food.R
import com.yape.food.model.RecipeItem
import com.yape.food.ui.MessageCardView
import com.yape.food.ui.theme.YapeFoodTheme


/**
 * 💡The recipes list view is like a RecyclerView in a compose way, so as a recycler view it
 * is good to define a "ViewHolder" that can be reused like down below.
 */
@Composable
fun RecipeListView(
    list: List<RecipeItem>,
    onSelect: (RecipeItem) -> Unit,
    modifier: Modifier = Modifier
) {
    if (list.isEmpty()) {
        //💡empty list placeholder
        RecipeListEmptyView(modifier = modifier.fillMaxWidth())
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(modifier = modifier) {
                //💡Header view
                item {
                    RecipeHeaderView(modifier = Modifier.height(180.dp))
                }
                //💡 It's a good practice to define the key of the item
                items(list, key = { recipe -> recipe.id }) { recipe ->
                    RecipeListItemView(
                        recipe = recipe,
                        modifier = Modifier
                            .height(260.dp)
                            .fillMaxWidth()
                    ) {
                        onSelect(recipe)
                    }
                }
            }
            RecipeSearchView(
                onSearch = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListViewPreview() {
    val list = (0..3).map {
        RecipeItem(
            id = it.toLong(),
            name = "Recipe Name"
        )
    }
    YapeFoodTheme {
        RecipeListView(list, {}, modifier = Modifier.fillMaxWidth())
    }
}

//💡Empty list placeholder when even a "success state" no items are presented
@Composable
fun RecipeListEmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Box {
            RecipeHeaderView(modifier = Modifier.height(160.dp))
            RecipeSearchView(
                onSearch = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(16.dp)
        ) {
            MessageCardView(
                title = stringResource(id = R.string.recipe_list_empty),
                subtitle = stringResource(id = R.string.recipe_list_empty_sub),
                background = painterResource(id = R.drawable.apple),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListLoadingViewPreview() {
    RecipeListEmptyView()
}


// Loading State
@Composable
fun RecipeListLoadingView(
    modifier: Modifier = Modifier,
    itemsCount: Int = 4,
) {

    Box(modifier = modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition(label = "infinite")
        val color by infiniteTransition.animateColor(
            initialValue = MaterialTheme.colorScheme.primaryContainer,
            targetValue = MaterialTheme.colorScheme.inversePrimary,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "color"
        )

        LazyColumn(modifier = modifier) {
            //💡Header view
            item {
                RecipeHeaderView(modifier = Modifier.height(180.dp))
            }

            //💡The intention is to create like a skeleton of a list, but quite simple with animation
            items(4) {
                Box(
                    modifier = Modifier
                        .height(260.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(1.dp)
                        .shadow(elevation = 4.dp, clip = false, shape = RoundedCornerShape(8.dp))
                        .drawBehind {
                            drawRect(color)
                        }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListLoadingPreview() {
    YapeFoodTheme {
        RecipeListLoadingView()
    }
}

// Error state ------
@Composable
fun RecipeListErrorView(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {

        LazyColumn(modifier = modifier) {
            //💡Header view
            item {
                RecipeHeaderView(modifier = Modifier.height(180.dp))
            }

            item {
                MessageCardView(
                    title = stringResource(id = R.string.recipe_list_error),
                    subtitle = stringResource(id = R.string.recipe_list_error_sub),
                    background = painterResource(id = R.drawable.apple),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListErrorViewPreview() {
    YapeFoodTheme {
        RecipeListErrorView()
    }
}