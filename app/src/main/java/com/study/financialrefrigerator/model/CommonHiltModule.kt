package com.study.financialrefrigerator.model

import android.content.Context
import androidx.room.Room
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
    fun getDataBase(@ApplicationContext context: Context): RefrigeratorDatabase =
        Room.databaseBuilder(context, RefrigeratorDatabase::class.java, "refrigerator")
            .createFromAsset("databases/refrigerator.db")
            .fallbackToDestructiveMigration()
            .build()
}
