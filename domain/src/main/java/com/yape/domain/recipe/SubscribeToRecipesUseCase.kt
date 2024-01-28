package com.yape.domain.recipe

import com.yape.data.recipe.IRecipeRepository
import com.yape.domain.model.Recipe
import com.yape.domain.model.toFlowRecipeList
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 💡Subscribes and maps to domain model (or business logic model) the recipe
 */
@ViewModelScoped
class SubscribeToRecipesUseCase @Inject constructor(
    private val repository: IRecipeRepository
) {
    //💡a single operation invoke can help to ensure 1 single action on use case
    operator fun invoke(): Flow<List<Recipe>> = repository.recipes.toFlowRecipeList()
}