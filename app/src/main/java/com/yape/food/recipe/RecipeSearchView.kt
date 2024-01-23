package com.yape.food.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yape.food.ui.theme.YapeFoodTheme

@Preview(showBackground = true)
@Composable
fun RecipeSearchViewPreview(modifier: Modifier = Modifier) {
    YapeFoodTheme {
        RecipeSearchView(onSearch = {})
    }
}


@Composable
fun RecipeSearchView(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    query: String = "",
) {
    Column(modifier = modifier) {
        TextField(
            value = query,
            onValueChange = { onSearch(it) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}