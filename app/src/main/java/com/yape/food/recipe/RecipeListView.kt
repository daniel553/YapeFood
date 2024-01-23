package com.yape.food.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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

                    RecipeHeaderView()
                }
                //💡 It's a good practice to define the key of the item
                items(list, key = { recipe -> recipe }) { recipe ->
                    RecipeListItemView(
                        recipe = recipe,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        onSelect(recipe)
                    }
                }
            }
            RecipeSearchView(onSearch = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListViewPreview() {
    val list = emptyList<RecipeItem>()
    YapeFoodTheme {
        RecipeListView(list, {})
    }
}

//💡Empty list placeholder when even a "success state" no items are presented
@Composable
fun RecipeListEmptyView(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListEmptyViewPreview() {
    RecipeListEmptyView()
}

//💡To be reused by the lazy column items
@Composable
fun RecipeListItemView(
    recipe: RecipeItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit // Intended to be empty
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .shadow(elevation = 4.dp, clip = false, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }) {
        Box {
            AsyncImage(model = recipe.url, contentDescription = "recipe")
            Text(
                text = recipe.name.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}
