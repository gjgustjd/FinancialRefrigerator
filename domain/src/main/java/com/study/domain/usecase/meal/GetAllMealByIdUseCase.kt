package com.study.domain.usecase.meal

import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllMealByIdUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope,id:Int) =
        withContext(scope.coroutineContext + Dispatchers.IO) {
            repository.getMealWithId(id)
        }

    suspend operator fun invoke(scope: CoroutineScope,id:Int) =
        execute(scope,id)
}