package com.yape.food.recipe.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yape.food.R
import com.yape.food.model.LocationItem
import com.yape.food.model.RecipeItem
import com.yape.food.ui.theme.YapeFoodTheme

@Preview
@Composable
fun RecipeDetailsViewPreview() {
    YapeFoodTheme {
        RecipeDetailsView(
            recipe = RecipeItem(
                0L,
                name = "Recipe name", description = "Description", rating = 5,
                ingredients = listOf("Ingredient", "Ingredient"),
                instructions = listOf("Instruction", "Instruction")
            ),
            onMapPressed = {},
            onBackPressed = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsView(
    recipe: RecipeItem,
    onMapPressed: (LocationItem) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    Box(modifier = modifier) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Row {
                        repeat(recipe.rating) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "stars",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.height(18.dp)
                            )
                        }
                    }

                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )

                    DetailedStringList(
                        title = stringResource(id = R.string.recipe_ingredients).uppercase(),
                        list = recipe.ingredients,
                        imageVector = Icons.Default.Add,
                        modifier = Modifier.fillMaxWidth()
                    )

                    DetailedStringList(
                        title = stringResource(id = R.string.recipe_instructions).uppercase(),
                        list = recipe.instructions,
                        imageVector = Icons.Default.KeyboardArrowRight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }

            },
            sheetContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            sheetPeekHeight = 260.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Row {
            FilledIconButton(onClick = { onBackPressed() }, modifier = Modifier.padding(16.dp)) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back")
            }
            Spacer(modifier = Modifier.weight(1f))
            FilledIconButton(
                onClick = { recipe.location?.let { onMapPressed(it) } },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "back")
            }
        }

    }
}


@Composable
fun DetailedStringList(
    title: String,
    list: List<String>,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    imageVector: ImageVector = Icons.Default.Check,
) {
    LazyColumn(modifier) {
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier.padding(bottom = 4.dp)
            )
        }
        items(list) {
            Row(modifier = Modifier.padding(4.dp)) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
