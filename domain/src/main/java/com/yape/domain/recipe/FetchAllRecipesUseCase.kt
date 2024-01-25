package com.yape.domain.recipe

import com.yape.data.recipe.IRecipeRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FetchAllRecipesUseCase @Inject constructor(
    private val repository: IRecipeRepository
) {
    //💡a single operation invoke can help to ensure 1 single action on use case
    suspend operator fun invoke(): Boolean = repository.fetch()
}