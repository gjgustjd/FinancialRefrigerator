package com.study.presentation.v2.di

import android.content.Context
import androidx.room.Room
import com.study.data.database.RefrigeratorDatabase
import com.study.data.repository.RefrigeratorRepositoryImpl
import com.study.data.repository.local.RefrigeratorLocalDataSource
import com.study.data.repository.local.RefrigeratorLocalDataSourceImpl
import com.study.data.repository.remote.RefrigeratorRemoteDataSource
import com.study.data.repository.remote.RefrigeratorRemoteDataSourceImpl
import com.study.data.repository.remote.api.AgriculturalProductApi
import com.study.domain.repository.RefrigeratorRepository
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

    @Provides
    @Singleton
    fun getLocalDataSource(database:RefrigeratorDatabase): RefrigeratorLocalDataSource =
            RefrigeratorLocalDataSourceImpl(database)

    @Provides
    @Singleton
    fun getRemoteDataSource(agriculturalProductApi: AgriculturalProductApi): RefrigeratorRemoteDataSource =
        RefrigeratorRemoteDataSourceImpl(agriculturalProductApi)

    @Provides
    @Singleton
    fun getRepository(localDatasource:RefrigeratorLocalDataSource,remoteDataSource: RefrigeratorRemoteDataSource): RefrigeratorRepository =
        RefrigeratorRepositoryImpl(localDatasource,remoteDataSource)
}
