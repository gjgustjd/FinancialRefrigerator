package com.study.domain.usecase.meal

import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteMealByIdUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    fun execute(scope: CoroutineScope,id:Int) =
        scope.launch(Dispatchers.IO) {
            repository.deleteMealWithId(id)
        }

    operator fun invoke(scope: CoroutineScope,id:Int) =
        execute(scope,id)
}