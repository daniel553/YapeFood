package com.yape.domain.recipe

import com.yape.data.recipe.IRecipeRepository
import com.yape.domain.model.Recipe
import com.yape.domain.model.toRecipe
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LoadRecipeByIdUseCase @Inject constructor(
    private val repository: IRecipeRepository
) {
    //💡Recipe
    suspend operator fun invoke(id: Long): Recipe = repository.getById(id).toRecipe()
}