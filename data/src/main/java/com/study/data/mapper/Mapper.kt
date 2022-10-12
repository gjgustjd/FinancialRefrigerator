package com.study.data.mapper

import com.study.data.model.ingredient.IngredientEntity
import com.study.data.model.meal.MealAndRecipeEntity
import com.study.data.model.meal.MealEntity
import com.study.data.model.recipe.RecipeEntity
import com.study.domain.model.IngredientItem
import com.study.domain.model.MealAndRecipeItem
import com.study.domain.model.MealItem
import com.study.domain.model.RecipeItem

fun IngredientEntity.toIngredientItem() =
    run { IngredientItem(id, name, description, quantity, unit, shelf_life) }

fun IngredientItem.toIngredientEntity() =
    run { IngredientEntity(id, name, description, quantity, unit, shelf_life)}

fun MealEntity.toMealItem() =
    run { MealItem(id,recipeKey)}

fun MealItem.toMealEntity()=
    run { MealEntity(id, recipeKey)}

fun MealAndRecipeEntity.toMealAndRecipeItem() =
    run { MealAndRecipeItem(meal.toMealItem(),recipe.toRecipeItem())}

fun MealAndRecipeItem.toMealAndRecipeEntity() =
    run { MealAndRecipeEntity(meal.toMealEntity(),recipe.toRecipeEntity()) }

fun RecipeEntity.toRecipeItem() =
    run { RecipeItem(id, name, imageUrl, ingredients, instruction, time) }

fun RecipeItem.toRecipeEntity():RecipeEntity =
    run { RecipeEntity(id, name, imageUrl, ingredients, instruction, time)}
