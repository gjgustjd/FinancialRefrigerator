package com.study.domain.usecase.local.meal

import com.study.domain.repository.RefrigeratorRepository
import javax.inject.Inject

class GetAllMealsWithRecipeUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute() = repository.getAllMealsWithRecipeAsFlow()
    suspend operator fun invoke() = execute()
}