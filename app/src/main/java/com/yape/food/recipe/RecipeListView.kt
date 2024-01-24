package com.yape.food.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yape.food.R
import com.yape.food.model.RecipeItem
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
            Box(
                Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apple),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.recipe_list_empty),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.recipe_list_empty_sub),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListEmptyViewPreview() {
    RecipeListEmptyView()
}