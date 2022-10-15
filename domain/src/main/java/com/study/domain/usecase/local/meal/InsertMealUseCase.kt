package com.study.domain.usecase.local.meal

import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertMealUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    fun execute(scope: CoroutineScope,id:Int) =
        scope.launch(Dispatchers.IO) {
            repository.insertMeal(id)
        }

    operator fun invoke(scope: CoroutineScope,id:Int) =
        execute(scope,id)
}