package com.study.financialrefrigerator.model

import android.content.Context
import androidx.room.Room
import com.study.financialrefrigerator.model.ingredient.IngredientsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonHiltModule {
    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): IngredientsDatabase =
        Room.databaseBuilder(context, IngredientsDatabase::class.java, "refrigerator")
            .fallbackToDestructiveMigration().build()
}
