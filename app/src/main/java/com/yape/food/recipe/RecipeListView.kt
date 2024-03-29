package com.yape.food.recipe

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yape.food.R
import com.yape.food.model.RecipeItem
import com.yape.food.ui.MessageCardView
import com.yape.food.ui.theme.YapeFoodTheme

val headerHeight = 180.dp

/**
 * 💡The recipes list view is like a RecyclerView in a compose way, so as a recycler view it
 * is good to define a "ViewHolder" that can be reused like down below.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeListView(
    list: List<RecipeItem>,
    onSelect: (RecipeItem) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    query: String,
) {

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag(RecipeListViewTag.ListTag.name)
        ) {
            //💡Header view
            item {
                if (list.isEmpty()) {
                    //💡empty list placeholder
                    RecipeListEmptyView(
                        query = query,
                        onSearch = { onSearch(it) },
                        modifier = modifier
                            .fillMaxWidth()
                            .height(headerHeight)
                            .testTag(RecipeListViewTag.EmptyTag.name)
                    )
                } else {
                    RecipeHeaderView(
                        modifier = Modifier
                            .height(headerHeight)
                            .testTag(
                                RecipeListViewTag.HeaderTag.name
                            )
                    )
                }
            }
            //💡 It's a good practice to define the key of the item
            items(list, key = { recipe -> recipe.id }) { recipe ->
                RecipeListItemView(
                    recipe = recipe,
                    modifier = Modifier
                        .height(itemViewHeight)
                        .fillMaxWidth()
                        .animateItemPlacement()
                        .testTag(RecipeListViewTag.ListItem.name)
                ) {
                    onSelect(recipe)
                }
            }
        }
        RecipeSearchView(
            query = query,
            onSearch = { onSearch(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp)
                .testTag(RecipeListViewTag.SearchTag.name)
        )
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
        RecipeListView(list, {}, {}, modifier = Modifier.fillMaxWidth(), "")
    }
}

//💡Empty list placeholder when even a "success state" no items are presented
@Composable
fun RecipeListEmptyView(query: String, onSearch: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Box {
            RecipeHeaderView(modifier = Modifier.height(160.dp))
            RecipeSearchView(
                onSearch = { onSearch(it) },
                query = query,
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
                .height(itemViewHeight)
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
    RecipeListEmptyView("", {})
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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            //💡Header view
            item {
                RecipeHeaderView(modifier = Modifier.height(headerHeight))
            }

            //💡The intention is to create like a skeleton of a list, but quite simple with animation
            items(4) {
                Box(
                    modifier = Modifier
                        .height(itemViewHeight)
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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            //💡Header view
            item {
                RecipeHeaderView(greetings = false, modifier = Modifier.height(headerHeight))
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

enum class RecipeListViewTag {
    HeaderTag,
    ListTag,
    SearchTag,
    EmptyTag,
    ListItem
}