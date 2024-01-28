package com.yape.food.recipe.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yape.food.model.RecipeItem
import com.yape.food.ui.theme.YapeFoodTheme


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val state = RecipeDetailsState.Success(
        recipe = RecipeItem(
            0L,
            name = "Recipe name", description = "Description", rating = 5,
            ingredients = listOf("Ingredient", "Ingredient"),
            instructions = listOf("Instruction", "Instruction")
        ),
    )
    YapeFoodTheme {
        RecipeDetailsScreen(state = state, onEvent = {})
    }
}

@Composable
fun RecipeDetailsScreen(
    state: RecipeDetailsState,
    onEvent: (RecipeDetailsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        when (state) {
            RecipeDetailsState.Error -> RecipeDetailsError(onBack = {
                onEvent(RecipeDetailsEvent.OnBack)
            })

            RecipeDetailsState.Loading -> RecipeDetailsLoading()
            is RecipeDetailsState.Success -> RecipeDetailsView(
                recipe = state.recipe,
                onMapPressed = {
                    onEvent(RecipeDetailsEvent.OnMapPressed(it))
                },
                onBackPressed = {
                    onEvent(RecipeDetailsEvent.OnBack)
                }
            )
        }
    }
}
