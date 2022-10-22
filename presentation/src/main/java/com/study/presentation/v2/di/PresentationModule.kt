package com.study.presentation.v2.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.presentation.v2.base.BaseLinearLayoutManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class PresentationModule {
    @Provides
    @ActivityScoped
    fun getVerticalLinearLayoutManager(@ActivityContext context: Context): LinearLayoutManager {
        return BaseLinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }

}
