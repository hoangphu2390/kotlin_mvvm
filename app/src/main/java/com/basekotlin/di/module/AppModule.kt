package com.basekotlin.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    lateinit var m_context: Context

    init {
        this.m_context = context;
    }

    @Provides
    @Singleton
    internal fun provideEventBus(): EventBus {
        return EventBus.getDefault()
    }
}