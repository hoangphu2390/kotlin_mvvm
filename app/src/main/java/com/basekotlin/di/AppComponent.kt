package com.basekotlin.di


import android.app.Application
import com.basekotlin.define.MyApplication
import com.basekotlin.di.builder.ActivityBuilder
import com.basekotlin.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, ActivityBuilder::class, AppModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

        fun appModule(appModule: AppModule): Builder
    }

    fun inject(app: MyApplication)
}