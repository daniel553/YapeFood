package com.yape.food.recipe.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yape.food.R
import com.yape.food.ui.MessageCardView
import com.yape.food.ui.theme.YapeFoodTheme

//---- Error

@Preview
@Composable
fun RecipeDetailsErrorPreview() {
    YapeFoodTheme {
        RecipeDetailsError {}
    }
}

@Composable
fun RecipeDetailsError(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        MessageCardView(
            title = stringResource(id = R.string.recipe_list_error),
            subtitle = stringResource(id = R.string.recipe_list_error_sub),
            background = painterResource(id = R.drawable.apple),
            modifier = Modifier.fillMaxSize()
        )
        FilledIconButton(
            onClick = { onBack() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
        }
    }
}


//---- Loading

@Preview
@Composable
fun RecipeDetailsLoadingPreview() {
    YapeFoodTheme {
        RecipeDetailsLoading()
    }
}

@Composable
fun RecipeDetailsLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        MessageCardView(
            title = stringResource(id = R.string.loading_title),
            subtitle = stringResource(id = R.string.loading_subtitle),
            background = painterResource(id = R.drawable.apple),
            modifier = Modifier.fillMaxSize()
        )

        CircularProgressIndicator(
            modifier = Modifier.width(84.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

    }
}