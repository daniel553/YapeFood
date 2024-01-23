package com.yape.food.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yape.food.R
import com.yape.food.ui.theme.YapeFoodTheme
import java.util.Calendar

@Preview(showBackground = true)
@Composable
fun RecipeHeaderViewPreview(
    modifier: Modifier = Modifier
) {
    YapeFoodTheme {
        RecipeHeaderView(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp))
    }
}

@Composable
fun RecipeHeaderView(
    modifier: Modifier = Modifier,
) {
    val time = Calendar.getInstance().asTimeRange()
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = time.asDrawableId()),
            contentDescription = "header",
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = stringResource(id = time.asResourceStringId()),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 8.dp)
        )
    }
}

private fun Calendar.asTimeRange(): Int {
    return when (this[Calendar.HOUR_OF_DAY]) {
        in 0..12 -> 0
        in 12..17 -> 1
        in 17..24 -> 2
        else -> 3
    }
}

private fun Int.asDrawableId(): Int = when (this) {
    0 -> R.drawable.ic_launcher_foreground
    1 -> R.drawable.ic_launcher_foreground
    2 -> R.drawable.ic_launcher_foreground
    else -> R.drawable.ic_launcher_foreground
}

private fun Int.asResourceStringId(): Int = when (this) {
    0 -> R.string.app_name
    1 -> R.string.app_name
    2 -> R.string.app_name
    else -> R.string.app_name
}


