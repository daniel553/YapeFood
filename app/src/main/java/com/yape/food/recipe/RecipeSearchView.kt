package com.yape.food.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        OutlinedTextField(
            value = query,
            onValueChange = { onSearch(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "search"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(0.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(32.dp)
                )
        )
    }
}